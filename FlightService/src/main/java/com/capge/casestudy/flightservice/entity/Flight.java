package com.capge.casestudy.flightservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "flights")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Flight number cannot be empty")
    private String flightNumber;

    @NotBlank(message = "Airline name cannot be empty")
    private String airline;

    @NotBlank(message = "Source cannot be empty")
    private String source;

    @NotBlank(message = "Destination cannot be empty")
    private String destination;

    @NotBlank(message = "Departure time cannot be empty")
    private String departureTime;


    @NotBlank(message = "Arrival time cannot be empty")
    private String arrivalTime;

    @NotNull(message = "Price must not be null")
    @Min(value = 0, message = "Price cannot be negative")
    private Double price;

    @Min(value = 1, message = "Total seats must be at least 1")
    private int totalSeats;

    @Min(value = 0, message = "Available seats cannot be negative")
    private int availableSeats;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate flightDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Arrival date must not be null")
    private LocalDate arrivalDate;


    @Enumerated(EnumType.STRING)
    private RecurrenceType recurrenceType; // DAILY, WEEKLY, MONTHLY

    private Boolean isRecurring = false;

    private LocalDate recurrenceEndDate; // Optional: when recurrence should stop

    public Flight(Flight other) {
        this.flightNumber = other.flightNumber;
        this.airline = other.airline;
        this.source = other.source;
        this.destination = other.destination;
        this.departureTime = other.departureTime;
        this.arrivalTime = other.arrivalTime;
        this.price = other.price;
        this.totalSeats = other.totalSeats;
        this.availableSeats = other.availableSeats;
        this.isRecurring = other.isRecurring;
        this.recurrenceType = other.recurrenceType;
        this.recurrenceEndDate = other.recurrenceEndDate;
        // flightDate and arrivalDate will be overwritten when duplicating
    }

}