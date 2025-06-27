package com.capge.casestudy.passengerservice.repository;

import com.capge.casestudy.passengerservice.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByPnr(String pnr);
   Optional<List<Passenger>> findByUserId(String userId);

    Passenger findByBookingId(Long bookingId);
}
