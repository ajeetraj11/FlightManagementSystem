package com.capge.casestudy.bookingservice.service.fiegn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "FLIGHT-SERVICE")
public interface FlightFeignClient {
    
    @PutMapping("/flights/updateSeats/{flightNumber}/{seats}")
    void updateAvailableSeats(@PathVariable String flightNumber, @PathVariable int seats);

    @GetMapping("/flights/get/Available/flightSeats/{flightNumber}")
    int getAvailableSeats(@PathVariable String flightNumber);
}