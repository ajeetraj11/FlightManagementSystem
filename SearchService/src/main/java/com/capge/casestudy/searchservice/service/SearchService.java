package com.capge.casestudy.searchservice.service;

import com.capge.casestudy.searchservice.dto.Flight;
import com.capge.casestudy.searchservice.service.Feign.FlightFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private FlightFeignService flightFeignService;

    //  Search direct flights
    public List<Flight> searchFlights(String source, String destination, LocalDate date) {
        List<Flight> allFlights = flightFeignService.getAllFlights();
        return allFlights.stream()
                .filter(f -> f.getSource().equalsIgnoreCase(source))
                .filter(f -> f.getDestination().equalsIgnoreCase(destination))
                .filter(f -> {
                    try {
                        return f.getFlightDate().equals(date);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    // Search by flight number
    public Flight searchByFlightNumber(String flightNumber) {
        List<Flight> allFlights = flightFeignService.getAllFlights();

        return allFlights.stream()
                .filter(f -> f.getFlightNumber().equalsIgnoreCase(flightNumber))
                .findFirst()
                .orElse(null);
    }

}
