package com.capge.casestudy.bookingservice.service.fiegn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentFeignClient {
    @DeleteMapping("/api/payments/deletePaymentBy/{bookingId}")
    void deletePaymentByPaymentId(@PathVariable Long bookingId);
}
