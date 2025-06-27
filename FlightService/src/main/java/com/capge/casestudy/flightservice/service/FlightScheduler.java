package com.capge.casestudy.flightservice.service;

import com.capge.casestudy.flightservice.entity.Flight;
import com.capge.casestudy.flightservice.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightScheduler {

    private final FlightRepository flightRepository;

    // Runs every day at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void generateDailyRecurringFlights() {
        LocalDate today = LocalDate.now();

        // Find base flights that should recur today
        List<Flight> recurringFlights = flightRepository.findAll().stream()
            .filter(flight -> Boolean.TRUE.equals(flight.getIsRecurring()))
            .filter(flight -> flight.getFlightDate().isBefore(today)) // Prevent duplicate generation
            .filter(flight -> flight.getRecurrenceEndDate() != null && !today.isAfter(flight.getRecurrenceEndDate()))
            .filter(flight -> shouldRecurOn(flight, today))
            .toList();

        List<Flight> newFlights = new ArrayList<>();
        for (Flight base : recurringFlights) {
            Flight newFlight = new Flight(base);
            newFlight.setId(null);
            newFlight.setFlightDate(today);
            newFlight.setArrivalDate(today); // adjust as needed
            newFlights.add(newFlight);
        }

        flightRepository.saveAll(newFlights);
    }

    private boolean shouldRecurOn(Flight flight, LocalDate date) {
        return switch (flight.getRecurrenceType()) {
            case DAILY -> true;
            case WEEKLY -> flight.getFlightDate().getDayOfWeek() == date.getDayOfWeek();
            case MONTHLY -> flight.getFlightDate().getDayOfMonth() == date.getDayOfMonth();
        };
    }
}