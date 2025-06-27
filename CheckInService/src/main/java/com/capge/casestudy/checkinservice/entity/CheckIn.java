package com.capge.casestudy.checkinservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String pnr;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String bookingId;

    @Column(nullable = false)
    private String flightNumber;

    @Column(nullable = false)
    private String status = "Unchecked";

    @Column(unique = true)
    private String selectedSeat;
}