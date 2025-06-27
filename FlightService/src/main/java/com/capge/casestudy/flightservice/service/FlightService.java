package com.capge.casestudy.flightservice.service;

import com.capge.casestudy.flightservice.entity.Flight;
import com.capge.casestudy.flightservice.exception.FormateNotAllowedException;
import com.capge.casestudy.flightservice.exception.NoFlightFoundException;
import com.capge.casestudy.flightservice.repository.FlightRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FlightService {

    private final FlightRepository flightRepository;

    // Constructor used to Initialize FlightRepository Instance
    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // createFlight method used to create Flights
    public Flight createFlight(@Valid Flight flight) throws FormateNotAllowedException {

        log.info("Attempting to create flight: {}", flight.getFlightNumber());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime departure = LocalTime.parse(flight.getDepartureTime(), timeFormatter);
        LocalTime arrival = LocalTime.parse(flight.getArrivalTime(), timeFormatter);

        LocalDate departureDate = flight.getFlightDate();
        LocalDate arrivalDate = flight.getArrivalDate();

        if (arrivalDate.isBefore(departureDate)) {
            throw new FormateNotAllowedException("Arrival date cannot be before departure date.");
        }

        if (arrivalDate.isEqual(departureDate) && !departure.isBefore(arrival)) {
            throw new FormateNotAllowedException("For same-day flights, departure time must be before arrival time.");
        }

        Optional<Flight> flightStatusCheck = flightRepository.findByFlightNumberAndFlightDate(flight.getFlightNumber(), flight.getFlightDate());
        if (flightStatusCheck.isPresent()) {
            log.warn("Flight {} already exists for departure time {}", flight.getFlightNumber(), flight.getDepartureTime());
            throw new FormateNotAllowedException("Flight with Flight Number " + flight.getFlightNumber() + " already created for day " + flight.getDepartureTime());
        }

        flight.setSource(flight.getSource().toLowerCase());
        flight.setDestination(flight.getDestination().toLowerCase());

        Flight savedFlight = flightRepository.save(flight);
        log.info("Flight {} created successfully", savedFlight.getFlightNumber());

        return savedFlight;

    }


    // getAllFlights method extract all Flights present in Database and Return into List
    public List<Flight> getAllFlights() {
        log.info("Fetching all flights from the database");
        return flightRepository.findAll();
    }

    //getFlightsByFlightNumberAndDepartureTime use to get flight exact match with flightNumber and given departureTime
    public Optional<Flight> getFlightsByFlightNumberAndDepartureTime(String flightNumber, LocalDate flightDate) {
        return flightRepository.findByFlightNumberAndFlightDate(flightNumber, flightDate);
    }

    // getFlightsBySourceAndDestination method used to get Flights Matched with source and destination
    public List<Flight> getFlightsBySourceAndDestination(String source, String destination) {
        return flightRepository.findBySourceAndDestination(source.toLowerCase(), destination.toLowerCase());
    }

    // getFlightsBySourceAndDestination method used to get Flights Matched with source and destination
    public List<Flight> getFlightsBySourceAndDestinationAndDate(String source, String destination , LocalDate date) {

        return flightRepository.findBySourceAndDestinationAndFlightDate(source.toLowerCase(), destination.toLowerCase() , date);
    }

    //getFlightByNUmber used to extract details of available flight for particular flightNumber
    public List<Flight> getFlightsByFlightNumber(String flightNumber) {
        List<Flight> flightList = flightRepository.findAll();

        return flightList.stream().
                filter( flight ->  flight.getFlightNumber().equalsIgnoreCase(flightNumber))
                .toList();
    }


    public Flight updateFlight(Long id ,  Flight updatedFlight) throws NoFlightFoundException,FormateNotAllowedException
    {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime departure = LocalTime.parse(updatedFlight.getDepartureTime(), timeFormatter);
        LocalTime arrival = LocalTime.parse(updatedFlight.getArrivalTime(), timeFormatter);

        LocalDate departureDate = updatedFlight.getFlightDate();
        LocalDate arrivalDate = updatedFlight.getArrivalDate();

        if (arrivalDate.isBefore(departureDate)) {
            throw new FormateNotAllowedException("Arrival date cannot be before departure date.");
        }

        if (arrivalDate.isEqual(departureDate) && !departure.isBefore(arrival)) {
            throw new FormateNotAllowedException("For same-day flights, departure time must be before arrival time.");
        }

        Optional<Flight> checkFlightExists = flightRepository.findById(id);
        if (!checkFlightExists.isPresent()) {
            throw new NoFlightFoundException("Flight not found with id " + id + " To Update ");
        }

        Flight existing = checkFlightExists.get();
        existing.setAirline(updatedFlight.getAirline());
        existing.setSource(updatedFlight.getSource());
        existing.setDestination(updatedFlight.getDestination());
        existing.setDepartureTime(updatedFlight.getDepartureTime());
        existing.setArrivalTime(updatedFlight.getArrivalTime());
        existing.setPrice(updatedFlight.getPrice());
        existing.setAvailableSeats(updatedFlight.getAvailableSeats());
        existing.setTotalSeats(updatedFlight.getTotalSeats());
        existing.setFlightDate(updatedFlight.getFlightDate());
        existing.setArrivalDate(updatedFlight.getArrivalDate());
        log.info("Flight with ID {} updated successfully", id);
        flightRepository.save(existing);
        return existing;

    }

    public void deleteFlight(Long id) throws NoFlightFoundException {
        Optional<Flight> existing = flightRepository.findById(id);
         if(!existing.isPresent()){
             throw new NoFlightFoundException("No Flight Found with id "+ id+" To delete");
         }
         flightRepository.deleteById(id);
    }

    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public void deleteById(Long id) {
        flightRepository.deleteById(id);
    }


    public void updateAvailableSeats(String flightNumber , int seatsToReduce) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber);
        if (flight != null && flight.getAvailableSeats() >= seatsToReduce) {
            flight.setAvailableSeats(flight.getAvailableSeats() - seatsToReduce);
            flightRepository.save(flight);
        } else {
            throw new RuntimeException("Not enough seats available for flight: " + flightNumber);
        }
    }

    public int getAvailableSeats(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber)==null ? 0 : flightRepository.findByFlightNumber(flightNumber).getAvailableSeats();
    }
}
