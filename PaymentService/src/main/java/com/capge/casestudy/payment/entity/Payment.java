package com.capge.casestudy.payment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotBlank(message = "PNR cannot be empty")
    private String pnr;

    @NotNull(message = "Booking Id cannot be null")
    private Long bookingId;

    @NotNull(message = "Amount must not be null")
    @Min(value = 1, message = "Amount must be greater than zero")
    private Double amount;

    @NotBlank(message = "Status cannot be empty")
    private String status;

    @NotNull(message = "Session Id cannot be null")
    private String sessionId;
    @Column(length = 5000)  // ✅ Defines a larger size
    @NotBlank(message = "Session URL cannot be empty")
    private String sessionUrl;
}