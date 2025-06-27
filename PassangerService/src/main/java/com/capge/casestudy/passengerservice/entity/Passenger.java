package com.capge.casestudy.passengerservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Booking ID cannot be null")
    @Column(unique = true)
    private Long bookingId;

    @NotBlank(message = "PNR cannot be empty")
    private String pnr;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @Column(name = "names", columnDefinition = "TEXT")
    @NotBlank(message = "names cannot be empty")
    private String names;

    @Min(value = 1, message = "Number of booked seats must be at least 1")
    private int numberOfBookedSeats;

    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotBlank(message = "Flight number cannot be empty")
    private String flightNumber;
}