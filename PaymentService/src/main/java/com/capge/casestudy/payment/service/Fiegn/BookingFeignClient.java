package com.capge.casestudy.payment.service.Fiegn;

import com.capge.casestudy.payment.dto.BookingData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "BOOKING-SERVICE")
public interface BookingFeignClient {
    @GetMapping("/bookings/getBookingById/{bookingId}")
    BookingData getBooking(@PathVariable Long bookingId);

    @PutMapping("/bookings/updateStatus/{bookingId}/{status}")
    void updateStatus(@PathVariable Long bookingId, @PathVariable String status);
}
