package com.capge.casestudy.checkinservice.repository;

import com.capge.casestudy.checkinservice.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    @Query("SELECT c.selectedSeat FROM CheckIn c WHERE c.flightNumber = :flightNumber")
    List<String> findBookedSeatsByFlightNumber(@Param("flightNumber") String flightNumber);

    CheckIn findByPnr(String pnr);

    Optional<CheckIn> findByBookingId(String bookingId);
}