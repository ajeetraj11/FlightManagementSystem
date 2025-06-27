package com.capge.casestudy.payment.service;

import com.capge.casestudy.payment.dto.BookingData;
import com.capge.casestudy.payment.dto.PaymentRequest;
import com.capge.casestudy.payment.dto.StripeSessionResponse;
import com.capge.casestudy.payment.entity.Payment;
import com.capge.casestudy.payment.repository.PaymentRepository;
import com.capge.casestudy.payment.service.Fiegn.BookingFeignClient;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

   @Autowired
    private final PaymentRepository paymentRepository;
    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    BookingFeignClient bookingFeignClient;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.currency}")
    private String currency;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public StripeSessionResponse createCheckoutSession(@Valid PaymentRequest paymentRequest) throws StripeException {

        log.info(paymentRequest.getPnr() + " : " + paymentRequest.getUserName() + " : " + paymentRequest.getUserEmail() + "  : ");
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:5173/get-bookings")
                    .setCancelUrl("http://localhost:5173")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(currency)
                                                    .setUnitAmount((long) (paymentRequest.getAmount() * 100))
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Flight Booking Payment")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .putMetadata("bookingId", String.valueOf(paymentRequest.getBookingId()))
                    .putMetadata("pnr", paymentRequest.getPnr())
                    .putMetadata("userName", paymentRequest.getUserName())
                    .putMetadata("userEmail", paymentRequest.getUserEmail())
                    .build();

            Session session = Session.create(params);
            System.out.println("Payment me Booking Id " + paymentRequest.getBookingId());
            // Save payment details in the database
            Payment payment = new Payment();
            payment.setUserId(paymentRequest.getUserId());
            payment.setPnr(paymentRequest.getPnr());
            payment.setBookingId(paymentRequest.getBookingId());
            payment.setAmount(paymentRequest.getAmount());
            payment.setStatus("PENDING");
            payment.setSessionId(session.getId());
            payment.setSessionUrl(session.getUrl());
            paymentRepository.save(payment);

            StripeSessionResponse stripeSessionResponse = new StripeSessionResponse(
                    session.getId(), session.getUrl(), payment.getId(),
                    paymentRequest.getPnr(), paymentRequest.getUserName(), paymentRequest.getAmount(), "Success", paymentRequest.getBookingId()
            );

                 BookingData booking = bookingFeignClient.getBooking(paymentRequest.getBookingId());
                // Send passenger creation request via RabbitMQ
                Map<String, Object> passengerData = Map.of(
                        "pnr", booking.getPnr(),
                        "userId", booking.getUserId(),
                        "bookingId", booking.getBookingId(),
                        "flightNumber", booking.getFlightNumber(),
                        "seats", booking.getNumberOfSeats(),
                        "names", booking.getNames(),
                        "email", booking.getUserEmail()
                );

                rabbitTemplate.convertAndSend("queue.booking.exchange", "queue.passenger.process", passengerData);
                log.info("Sent Passenger Creation request to queue.");

                log.info("Successfully sent to booking and saved payment in DB");
                return stripeSessionResponse;

            } catch(StripeException e){
                log.error("Failed to create Stripe Checkout Session: {}", e.getMessage());
                throw new RuntimeException("Stripe Checkout Session creation failed");
            }
        }

    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }

    // Runs every 60 seconds
    @Scheduled(fixedDelay = 60000)
    public void checkPendingPayments() {
        List<Payment> pendingPayments = paymentRepository.findByStatus("PENDING");
        for (Payment payment : pendingPayments) {
            checkPaymentStatus(payment.getSessionId());
        }
    }
    public void checkPaymentStatus(String sessionId) {
        try {
            Session session = Session.retrieve(sessionId);
            if ("complete".equalsIgnoreCase(session.getStatus())) {
                Payment payment = paymentRepository.findBySessionId(sessionId);
                if (payment != null) {
                    payment.setStatus("SUCCESS");
                    paymentRepository.save(payment);
                    bookingFeignClient.updateStatus(payment.getBookingId() , "SUCCESS");
                    log.info("Payment status updated to SUCCESS for sessionId: {}", sessionId);
                }
            }
        } catch (StripeException e) {
            log.error("Error retrieving payment session: {}", e.getMessage());
        }
    }

    public List<Payment> getAllPaymentsByUserId(String userId){
        return paymentRepository.findByUserId(userId);
    }
    public void deletePaymentByBookingId(Long BookingId) {
        paymentRepository.deleteByBookingId(BookingId);
    }




}
