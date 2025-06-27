package com.capge.casestudy.flightservice.controller;
import com.capge.casestudy.flightservice.entity.Flight;
import com.capge.casestudy.flightservice.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/getFlightById/{id}")
    public ResponseEntity<Optional<Flight>> getFlightById(@PathVariable Long id){
        return new ResponseEntity<>(flightService.getFlightById(id), HttpStatus.OK);
    }

    // 2. Get Flight Based On GivenDate and FlightNumber

    @GetMapping("/{flightNumber}/{flightDate}")
    public ResponseEntity<Optional<Flight>> getFlightByFlightNumberAndDepartureTime(@PathVariable String flightNumber , @PathVariable LocalDate flightDate)
    {
        return ResponseEntity.ok(flightService.getFlightsByFlightNumberAndDepartureTime(flightNumber, flightDate));
    }



    // 3. Get all flights
    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }


    // 4. get Flights by source and destination
    @GetMapping("/getFlight/{source}/{destination}")
    public ResponseEntity<List<Flight>> getFlightBySourceAndDestination(@PathVariable String source , @PathVariable  String destination) {
        return ResponseEntity.ok(flightService.getFlightsBySourceAndDestination(source ,destination));
    }

    //5 get All Flights by flight Number
    @GetMapping("/getFlightBy/{flightNumber}")
    public ResponseEntity<List<Flight>> getFlightsByFlightNumber(@PathVariable String flightNumber){
        return ResponseEntity.ok(flightService.getFlightsByFlightNumber(flightNumber));
    }



    // 6. get Flights by source and destination and date
    @GetMapping("/getFlightBy/{source}/{destination}/{date}")
    public ResponseEntity<List<Flight>> getFlightBySourceAndDestination(@PathVariable String source , @PathVariable  String destination , @PathVariable LocalDate date) {
        return ResponseEntity.ok(flightService.getFlightsBySourceAndDestinationAndDate(source ,destination ,date));
    }

    // 7. update the Available seats in respective flight
    @PutMapping("/updateSeats/{flightNumber}/{seats}")
    public void updateAvailableSeats(@PathVariable String flightNumber, @PathVariable int seats) {
        flightService.updateAvailableSeats(flightNumber, seats);
    }


    // 8. get the Available seat in the flight
    @GetMapping("/get/Available/flightSeats/{flightNumber}")
    public int getAvailableSeats(@PathVariable String flightNumber){
        return  flightService.getAvailableSeats(flightNumber);
    }


}
