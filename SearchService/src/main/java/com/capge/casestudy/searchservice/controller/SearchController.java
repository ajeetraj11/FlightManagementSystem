package com.capge.casestudy.searchservice.controller;

import com.capge.casestudy.searchservice.dto.Flight;
import com.capge.casestudy.searchservice.service.ConnectedFlightSearch;
import com.capge.casestudy.searchservice.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private ConnectedFlightSearch connectedFlightSearch;

    // Search by source, destination, and date
    @GetMapping("/{source}/{destination}/{date}")
    public List<Flight> searchFlights(@PathVariable String source,
                                      @PathVariable String destination,
                                      @PathVariable LocalDate date) {
        return searchService.searchFlights(source, destination, date);
    }

    // Search by flight number
    @GetMapping("/flight/{flightNumber}")
    public Flight getFlightByNumber(@PathVariable String flightNumber) {
        return searchService.searchByFlightNumber(flightNumber);
    }

    @GetMapping("/connectivity/{source}/{destination}/{date}")
    public ResponseEntity<?> searchConnected(
            @PathVariable String source,
            @PathVariable String destination,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            System.out.println("call initiate");
            List<List<Flight>> routes = connectedFlightSearch.searchConnectedFlights(source , destination , date);
            System.out.println("data bhi aa gya" + routes);
            return ResponseEntity.ok(routes);
        } catch (Exception e) {
            e.printStackTrace(); // Replace with proper logger
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error searching for connected flights.");
        }
    }


}
