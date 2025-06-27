package com.capge.casestudy.bookingservice.service;
import com.capge.casestudy.bookingservice.entity.Booking;
import com.capge.casestudy.bookingservice.exception.BookingNotFoundException;
import com.capge.casestudy.bookingservice.exception.SeatUnavailableException;
import com.capge.casestudy.bookingservice.repository.BookingRepository;
import com.capge.casestudy.bookingservice.service.fiegn.FlightFeignClient;
import com.capge.casestudy.bookingservice.service.fiegn.PaymentFeignClient;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private FlightFeignClient flightFeignClient;

    @Autowired
    private PaymentFeignClient paymentFeignClient;

    // Todo: create a method to save the booking instance in DB IF flight seat available
    @Override
    public Booking createBooking(@Valid Booking booking) {
        int availableSeats = flightFeignClient.getAvailableSeats(booking.getFlightNumber());
        if (booking.getNumberOfSeats() > availableSeats) {
            log.info("Insufficient seats in flight with flightNumber {}",booking.getFlightNumber());
            throw new SeatUnavailableException("Insufficient seats for flight: " + booking.getFlightNumber());
        }
         booking.setStatus("PENDING");
         booking.setPnr(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
         flightFeignClient.updateAvailableSeats(booking.getFlightNumber() , booking.getNumberOfSeats());
        return  bookingRepository.save(booking);
    }


    @Override
    public Booking getBookingByPNR(String pnr) {
        return bookingRepository.findByPnr(pnr);
    }

    @Override
    public Booking updateBooking(Long id, Booking bookingDetails) throws BookingNotFoundException {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (!optionalBooking.isPresent()) {
            throw new BookingNotFoundException("Booking not found with id " + id);
        }

        Booking existingBooking = optionalBooking.get();

        // Update relevant fields except bookingId (primary key)
        existingBooking.setUserId(bookingDetails.getUserId());
        existingBooking.setFlightNumber(bookingDetails.getFlightNumber());
        existingBooking.setBookingDate(bookingDetails.getBookingDate());
        existingBooking.setStatus(bookingDetails.getStatus());
        existingBooking.setNumberOfSeats(bookingDetails.getNumberOfSeats());
        existingBooking.setTotalAmount(bookingDetails.getTotalAmount());
        existingBooking.setPnr(bookingDetails.getPnr());
        existingBooking.setUserEmail(bookingDetails.getUserEmail());
        log.info("booking details successfully updated with booking ID,{}", existingBooking.getBookingId());
        return bookingRepository.save(existingBooking);
    }

    @Override
    public void deleteBooking(Long id) throws BookingNotFoundException {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (!optionalBooking.isPresent()) {
            throw new BookingNotFoundException("Booking not found with id " + id);
        }

        log.info("Booking Found Now We Can Proceed to Delete Booking");
        String flightNumber = optionalBooking.get().getFlightNumber();
        flightFeignClient.updateAvailableSeats(flightNumber , - optionalBooking.get().getNumberOfSeats());
        paymentFeignClient.deletePaymentByPaymentId(id);
        bookingRepository.deleteById(id);
    }


    @Override
    public List<Booking> getAllBooking(String usedId) {
        return bookingRepository.findByUserId(usedId);
    }

    @Override
    public List<Booking> getAllBooking(){
        return bookingRepository.findAll();
    }


    @Override
    public Optional<Booking> getBookingById(Long id){
        return bookingRepository.findById(id);
    }


    @Override
    public void updateStatus(Long id , String status){
        System.out.println("Called ");
        Optional<Booking> booking = bookingRepository.findById(id);
        if(booking.isPresent()){
            booking.get().setStatus(status);
            bookingRepository.save(booking.get());
        }
    }
}
