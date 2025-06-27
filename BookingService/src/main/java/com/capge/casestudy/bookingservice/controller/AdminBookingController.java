package com.capge.casestudy.bookingservice.controller;

import com.capge.casestudy.bookingservice.entity.Booking;
import com.capge.casestudy.bookingservice.exception.BookingNotFoundException;
import com.capge.casestudy.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/bookings")
public class AdminBookingController {

    private final BookingService bookingService;

    @Autowired
    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    // Update booking details by ID (full update or partial update except status)
    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) throws BookingNotFoundException {
        return bookingService.updateBooking(id, booking);
    }

    // getAll Bookings
    @GetMapping("/getAllBookings")
    public List<Booking> getAllBookings(){
        return bookingService.getAllBooking();
    }
}
