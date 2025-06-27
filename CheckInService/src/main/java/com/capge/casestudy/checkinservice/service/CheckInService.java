package com.capge.casestudy.checkinservice.service;

import com.capge.casestudy.checkinservice.dto.PassengerDTO;
import com.capge.casestudy.checkinservice.entity.CheckIn;
import com.capge.casestudy.checkinservice.exception.DuplicateCheckInException;
import com.capge.casestudy.checkinservice.repository.CheckInRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
public class CheckInService {

    @Autowired
    private CheckInRepository repository;

    private static final List<String> TOTAL_SEATS = generateSeatNumbers(100);

    public CheckIn saveCheckIn(PassengerDTO dto) {
        // Check if the booking ID already exists
        Optional<CheckIn> existingCheckIn = repository.findByBookingId(dto.getBookingId());
        if (existingCheckIn.isPresent()) {
            throw new DuplicateCheckInException("Booking ID " + dto.getBookingId() + " has already been checked in.");
        }

        List<String> bookedSeats = Optional.ofNullable(repository.findBookedSeatsByFlightNumber(dto.getFlightNumber()))
                .orElse(Collections.emptyList());

        List<String> availableSeats = TOTAL_SEATS.stream()
                .filter(seat -> !bookedSeats.contains(seat))
                .collect(Collectors.toList());

        String assignedSeat = availableSeats.get(new Random().nextInt(availableSeats.size()));

        CheckIn checkIn = new CheckIn();
        checkIn.setPnr(dto.getPnr());
        checkIn.setBookingId(dto.getBookingId());
        checkIn.setUserId(dto.getUserId());
        checkIn.setFlightNumber(dto.getFlightNumber());
        checkIn.setStatus("Checked-In");
        checkIn.setSelectedSeat(assignedSeat);

        return repository.save(checkIn);
    }
    public CheckIn getCheckInByPNR(String pnr) {
        return repository.findByPnr(pnr);
    }

    private static List<String> generateSeatNumbers(int count) {
        List<String> seatNumbers = new ArrayList<>();
        char[] rows = {'A', 'B', 'C', 'D', 'E', 'F'};
        int rowCount = (int) Math.ceil((double) count / rows.length);

        for (int i = 1; i <= rowCount; i++) {
            for (char row : rows) {
                seatNumbers.add(i + String.valueOf(row));
                if (seatNumbers.size() >= count) break;
            }
        }
        return seatNumbers;
    }
}