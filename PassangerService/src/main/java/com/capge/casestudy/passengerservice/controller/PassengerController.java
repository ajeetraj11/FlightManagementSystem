package com.capge.casestudy.passengerservice.controller;

import com.capge.casestudy.passengerservice.entity.Passenger;
import com.capge.casestudy.passengerservice.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    private final PassengerService passengerService;
    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping("getPassengerByBookingId/{bookingId}")
    public Passenger getPassengerByBookingId(@PathVariable Long bookingId){
        return passengerService.getPassengerByBookingId(bookingId);
    }

    @GetMapping("/getPassengerByUserId/{userId}")
    public Optional<List<Passenger>> getPassengerById(@PathVariable String userId){
        return passengerService.getPassengerById(userId);
    }



    @DeleteMapping("/deletePassengerByBookingId/{bookingId}")
    public void deleteByPassengerId(@PathVariable Long passengerId){
        passengerService.deletePassenger(passengerId);
    }

}