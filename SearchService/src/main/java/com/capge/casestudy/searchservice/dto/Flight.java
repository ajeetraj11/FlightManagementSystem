package com.capge.casestudy.searchservice.dto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class Flight {



    private String flightNumber;
    private String airline;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private Double price;
    private int totalSeats;
    private int availableSeats;

    private LocalDate flightDate;
    private LocalDate arrivalDate;
    @Enumerated(EnumType.STRING)
    private RecurrenceType recurrenceType;
    private Boolean isRecurring = false;
    private LocalDate recurrenceEndDate;

    public LocalTime getDepartureLocalTime() {
        try {
            return LocalTime.parse(departureTime.replace(";", ":").trim(), DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            System.err.println("Invalid departureTime: " + departureTime);
            return null;
        }
    }

    public LocalTime getArrivalLocalTime() {
        try {
            return LocalTime.parse(arrivalTime.replace(";", ":").trim(), DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            System.err.println("Invalid arrivalTime: " + arrivalTime);
            return null;
        }
    }

}
