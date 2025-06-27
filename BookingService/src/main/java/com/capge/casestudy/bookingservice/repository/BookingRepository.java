package com.capge.casestudy.bookingservice.repository;


import com.capge.casestudy.bookingservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(String userId);
    Booking findByPnr(String pnr);

}
