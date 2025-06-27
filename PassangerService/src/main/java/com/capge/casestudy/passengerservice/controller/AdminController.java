package com.capge.casestudy.passengerservice.controller;

import com.capge.casestudy.passengerservice.entity.Passenger;
import com.capge.casestudy.passengerservice.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/passenger/admin")
public class AdminController {

    @Autowired
    private PassengerService passengerService;


    @PutMapping("/updatePassenger/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody Passenger passengerDetails) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, passengerDetails));
    }

    @DeleteMapping("/deletePassenger/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllPassenger")
    public ResponseEntity<List<Passenger>> getAllUser(){
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

}
