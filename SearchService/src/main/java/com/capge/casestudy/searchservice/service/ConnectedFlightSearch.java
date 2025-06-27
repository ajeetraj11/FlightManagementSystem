package com.capge.casestudy.searchservice.service;


import com.capge.casestudy.searchservice.dto.Flight;
import com.capge.casestudy.searchservice.service.Feign.FlightFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ConnectedFlightSearch {
    @Autowired
    private FlightFeignService flightFeignService;

    public List<List<Flight>> searchConnectedFlights(String source, String destination, LocalDate date) {
        List<Flight> allFlights = flightFeignService.getAllFlights();

        if (allFlights == null || allFlights.isEmpty()) return Collections.emptyList();

        // Filter flights only for the given date
        List<Flight> sameDayFlights = allFlights.stream()
                .filter(f -> f != null && f.getFlightDate() != null && date.equals(f.getFlightDate()))
                .collect(Collectors.toList());

        List<List<Flight>> results = new ArrayList<>();
        System.out.println("Before DFS");
        dfs(source, destination, new ArrayList<>(), results, sameDayFlights, date);
        System.out.println("After DFS");
        return results;
    }

    private void dfs(String current, String destination,
                     List<Flight> path, List<List<Flight>> results,
                     List<Flight> flights, LocalDate date) {

        if (current.equalsIgnoreCase(destination)) {
            if (!path.isEmpty()) {
                results.add(new ArrayList<>(path));
            }
            return;
        }

        for (Flight flight : flights) {
            if (flight == null) continue;

            String flightNumber = flight.getFlightNumber();
            String flightSource = flight.getSource();
            String flightDest = flight.getDestination();

            if (flightNumber == null || flightSource == null || flightDest == null) continue;

            // Ensure flight departs on the same date
            if (!date.equals(flight.getFlightDate())) continue;

            // Avoid cycles
            if (!flightSource.equalsIgnoreCase(current) ||
                    path.stream().anyMatch(p -> flightNumber.equals(p.getFlightNumber()))) {
                continue;
            }

            // Layover validation
            if (!path.isEmpty()) {
                Flight lastFlight = path.get(path.size() - 1);
                LocalTime lastArrival = lastFlight.getArrivalLocalTime();
                LocalTime nextDeparture = flight.getDepartureLocalTime();

                if (lastArrival == null || nextDeparture == null) continue;

                // Require minimum 1-hour layover
                if (!nextDeparture.isAfter(lastArrival.plusHours(1))) continue;
            }

            path.add(flight);
            dfs(flightDest, destination, path, results, flights, date);
            path.remove(path.size() - 1);
        }
    }
}
