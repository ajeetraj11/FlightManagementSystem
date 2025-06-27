package com.capge.casestudy.bookingservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private Long bookingId;

    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotEmpty(message = "Flight number cannot be empty")
    private String flightNumber;

    @NotNull(message = "Booking date must not be null")
    private LocalDateTime bookingDate;

    @NotNull(message = "flight date must not be null")
    private LocalDate flightDate;

    @NotEmpty(message = "Status cannot be empty")
    private String status;

    @Min(value = 1, message = "Number of seats must be at least 1")
    private int numberOfSeats;

    @Min(value = 0, message = "Total amount cannot be negative")
    private double totalAmount;

    private String pnr;

    @Email(message = "User email must be valid")
    @NotBlank(message = "User email cannot be empty")
    @NotNull(message = "email cannot be null")
    private String userEmail;

    @NotNull(message = "Passenger names cannot be null")
    @Size(min = 1, message = "At least one passenger name must be provided")
    private String names;


}