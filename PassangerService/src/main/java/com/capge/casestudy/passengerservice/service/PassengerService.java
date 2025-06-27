package com.capge.casestudy.passengerservice.service;

import com.capge.casestudy.passengerservice.entity.Passenger;
import com.capge.casestudy.passengerservice.repository.PassengerRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository ) {
        this.passengerRepository = passengerRepository;
    }


    public Passenger savePassenger(@Valid Map<String, Object> payload) {
        Optional<Passenger> existing = passengerRepository.findByPnr((String) payload.get("pnr"));
        if (existing.isPresent()) {
            return existing.get();
        }
        log.info("Passenger Processing Initiated");
        Passenger passenger = new Passenger();
        passenger.setPnr((String) payload.get("pnr"));
        passenger.setBookingId(Long.valueOf(payload.get("bookingId").toString()));
        passenger.setEmail((String) payload.get("email"));

        String names = (String) payload.get("names");
        passenger.setNames(names);

        passenger.setNumberOfBookedSeats(Integer.parseInt(payload.get("seats").toString()));
        passenger.setFlightNumber((String) payload.get("flightNumber"));
        passenger.setUserId((String) payload.get("userId"));

        log.info("Saving Passenger: {}", passenger);
        return passengerRepository.save(passenger);
    }


    public Passenger updatePassenger(Long id, Passenger passengerDetails) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    passenger.setEmail(passengerDetails.getEmail());
                    passenger.setNames(passengerDetails.getNames());
                    passenger.setNumberOfBookedSeats(passengerDetails.getNumberOfBookedSeats());
                    return passengerRepository.save(passenger);
                }).orElseThrow(() -> new RuntimeException("Passenger not found"));
    }


    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }


    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Optional<List<Passenger>> getPassengerById(String userId) {
        log.info("Displaying All Available Passenger of Current user");
        return passengerRepository.findByUserId(userId);
    }

    public Passenger getPassengerByBookingId(Long bookingId) {
        return passengerRepository.findByBookingId(bookingId);
    }
}
