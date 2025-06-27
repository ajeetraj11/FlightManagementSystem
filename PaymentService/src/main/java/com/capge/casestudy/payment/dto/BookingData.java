package com.capge.casestudy.payment.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingData {

    private Long bookingId;
    private String userId;
    private String flightNumber;
    private LocalDateTime bookingDate;
    private String status;
    private int numberOfSeats;
    private double totalAmount;
    private String pnr;
    private String userEmail;
    private String names;


}