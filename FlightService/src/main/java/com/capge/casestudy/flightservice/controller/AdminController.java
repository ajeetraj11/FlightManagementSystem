package com.capge.casestudy.flightservice.controller;

import com.capge.casestudy.flightservice.entity.Flight;
import com.capge.casestudy.flightservice.exception.FormateNotAllowedException;
import com.capge.casestudy.flightservice.exception.NoFlightFoundException;
import com.capge.casestudy.flightservice.service.FlightService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/flight")
public class AdminController {


    private FlightService flightService;
    @Autowired
    public AdminController(FlightService flightService) {
        this.flightService = flightService;
    }

    // 1. Create flight

    @PostMapping("/createFlight")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) throws FormateNotAllowedException {
        return ResponseEntity.ok(flightService.createFlight(flight));
    }

    // 2. Get all flights
    @GetMapping("/getAllFlights")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    // 3. Get flight by ID
    @GetMapping("/getFlightById/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) throws NoFlightFoundException {
        Optional<Flight> flight = flightService.getFlightById(id);
        if (flight.isEmpty()) {
            throw new NoFlightFoundException("Flight not found with id: " + id);
        }
        return ResponseEntity.ok(flight.get());
    }

    // 4. Update flight by ID
    @PutMapping("/updateFlightById/{id}")
    public ResponseEntity<Flight> updateFlightById(@PathVariable Long id, @RequestBody Flight updatedFlight)
            throws NoFlightFoundException {
        Optional<Flight> existing = flightService.getFlightById(id);
        if (existing.isEmpty()) {
            throw new NoFlightFoundException("No flight found with id: " + id);
        }

        Flight flightToUpdate = existing.get();
        flightToUpdate.setAirline(updatedFlight.getAirline());
        flightToUpdate.setSource(updatedFlight.getSource());
        flightToUpdate.setDestination(updatedFlight.getDestination());
        flightToUpdate.setDepartureTime(updatedFlight.getDepartureTime());
        flightToUpdate.setArrivalTime(updatedFlight.getArrivalTime());
        flightToUpdate.setPrice(updatedFlight.getPrice());
        flightToUpdate.setTotalSeats(updatedFlight.getTotalSeats());
        flightToUpdate.setAvailableSeats(updatedFlight.getAvailableSeats());

        return ResponseEntity.ok(flightService.saveFlight(flightToUpdate));
    }

    // 5. Delete flight by ID
    @DeleteMapping("/deleteFlightById/{id}")
    public ResponseEntity<String> deleteFlightById(@PathVariable Long id) throws NoFlightFoundException {
        Optional<Flight> existing = flightService.getFlightById(id);
        if (existing.isEmpty()) {
            throw new NoFlightFoundException("No flight found with id: " + id);
        }

        flightService.deleteById(id);
        return ResponseEntity.ok("Flight deleted successfully.");
    }
}
