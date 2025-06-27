package com.capge.casestudy.bookingservice.service;


import com.capge.casestudy.bookingservice.entity.Booking;
import com.capge.casestudy.bookingservice.exception.BookingNotFoundException;
import com.capge.casestudy.bookingservice.exception.SeatUnavailableException;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking createBooking(Booking booking) throws SeatUnavailableException;

    Booking getBookingByPNR(String pnr);

    Booking updateBooking(Long id, Booking bookingDetails) throws  BookingNotFoundException;

    void deleteBooking(Long id) throws BookingNotFoundException;

    List<Booking> getAllBooking(String usedId);


    List<Booking> getAllBooking();

    Optional<Booking> getBookingById(Long id);

    void updateStatus(Long id, String status);
}
