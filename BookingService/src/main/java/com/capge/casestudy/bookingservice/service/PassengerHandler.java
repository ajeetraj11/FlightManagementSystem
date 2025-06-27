package com.capge.casestudy.bookingservice.service;

import com.capge.casestudy.bookingservice.entity.Booking;
import com.capge.casestudy.bookingservice.repository.BookingRepository;
import com.capge.casestudy.bookingservice.service.fiegn.FlightFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class PassengerHandler {

    private final BookingRepository bookingRepository;
    private final FlightFeignClient flightFeignClient;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public PassengerHandler(BookingRepository bookingRepository, FlightFeignClient flightFeignClient, RabbitTemplate rabbitTemplate) {
        this.bookingRepository = bookingRepository;
        this.flightFeignClient = flightFeignClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "queue.payment.process")
    public void processPayment(Map<String, Object> paymentData) {
        log.info("Received Payment Data: {}", paymentData);

        String pnr = (String) paymentData.get("pnr");
        Long bookingId = Long.valueOf(paymentData.get("bookingId").toString());
        String status = (String) paymentData.get("status");

        Booking booking = bookingRepository.findByPnr(pnr);

        if (booking == null) {
            log.warn("Booking not found for PNR: {}", pnr);
            return;
        }

        if ("Success".equalsIgnoreCase(status)) {
            booking.setStatus("CONFIRMED");
            bookingRepository.save(booking);

            // Call Flight Service via Feign to update seat count
            flightFeignClient.updateAvailableSeats(booking.getFlightNumber(), booking.getNumberOfSeats());

            log.info("Booking confirmed and seats updated for PNR: {}", pnr);

            // Send Passenger Data to RabbitMQ Queue
            Map<String, Object> passengerData = Map.of(
                    "pnr", booking.getPnr(),
                    "userId", booking.getUserId(),
                    "bookingId", booking.getBookingId(),
                    "flightNumber", booking.getFlightNumber(),
                    "seats", booking.getNumberOfSeats(),
                    "names", booking.getNames(),
                    "email", booking.getUserEmail()
            );

            rabbitTemplate.convertAndSend("queue.passenger.exchange", "queue.response.key.passenger", passengerData);

            log.info("Sent Passenger Creation request to queue: {}", passengerData);
        } else {
            booking.setStatus("FAILED");
            bookingRepository.save(booking);
            log.warn("Payment failed, booking status updated to FAILED for PNR: {}", pnr);
        }
    }
}