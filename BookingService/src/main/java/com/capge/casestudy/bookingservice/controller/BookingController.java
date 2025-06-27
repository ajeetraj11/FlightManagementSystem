package com.capge.casestudy.bookingservice.controller;
import com.capge.casestudy.bookingservice.entity.Booking;
import com.capge.casestudy.bookingservice.exception.BookingNotFoundException;
import com.capge.casestudy.bookingservice.service.BookingService;
import com.capge.casestudy.bookingservice.service.BookingServiceImpl;
import com.capge.casestudy.bookingservice.service.fiegn.FlightFeignClient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
   public  BookingController(BookingService bookingService){

        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBooking(@Valid @RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @GetMapping("/getBookingById/{id}")
    public Optional<Booking> getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id);
    }

    @GetMapping("/getAllBookings/{userId}")
    public  List<Booking> getAllBooking(@PathVariable String userId){
        return bookingService.getAllBooking(userId);
    }

    @GetMapping("/getBookingByPnr/{pnr}")
    public Booking getByPNR(@PathVariable String pnr) {
        return bookingService.getBookingByPNR(pnr);
    }


    // Delete booking by ID
    @DeleteMapping("/deleteBookingById/{id}")
    public void deleteBooking(@PathVariable Long id) throws BookingNotFoundException {
        bookingService.deleteBooking(id);
    }

    @PutMapping("/updateStatus/{id}/{status}")
    public void updateStatus(@PathVariable Long id , @PathVariable String status){
        bookingService.updateStatus(id , status);
    }
}
