package com.capge.casestudy.flightservice.repository;

import com.capge.casestudy.flightservice.entity.Flight;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Flight findByFlightNumber(String flightNumber);

    Flight findByFlightDate(LocalDate flightDate);
    Optional<Flight> findByFlightNumberAndFlightDate(String flightNumber, LocalDate flightDate);
    List<Flight> findBySourceAndDestination(String source ,String destination);

    List<Flight> findBySourceAndDestinationAndFlightDate(String source, String destination, LocalDate flightDate);





}
