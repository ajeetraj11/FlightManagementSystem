package com.capge.casestudy.payment.controller;

import com.capge.casestudy.payment.dto.PaymentRequest;
import com.capge.casestudy.payment.dto.StripeSessionResponse;
import com.capge.casestudy.payment.entity.Payment;
import com.capge.casestudy.payment.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @GetMapping("/payment-status/{sessionId}")
    public ResponseEntity<String> checkPayment(@PathVariable String sessionId) {
        paymentService.checkPaymentStatus(sessionId);
        return ResponseEntity.ok("Checked payment status for sessionId: " + sessionId);
    }
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<StripeSessionResponse> createCheckoutSession(@RequestBody PaymentRequest paymentRequest) throws StripeException {
        StripeSessionResponse sessionResponse = paymentService.createCheckoutSession(paymentRequest);
        return ResponseEntity.ok(sessionResponse);
    }

    @DeleteMapping("/deletePaymentBy/{bookingId}")
    public void deletePaymentByPaymentId(@PathVariable Long bookingId){
        paymentService.deletePaymentByBookingId(bookingId);
    }

    @GetMapping("/getAllPayments")
    public List<Payment> getAllPayments(){
        return paymentService.getAllPayments();
    }

    @GetMapping("/getPaymentsByUserId/{userId}")
    public List<Payment> getAllPaymentOfUser(@PathVariable String userId){
        return paymentService.getAllPaymentsByUserId(userId);
    }


}
