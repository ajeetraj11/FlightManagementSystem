package com.capge.casestudy.checkinservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDTO {
    private String pnr;
    private String userId;
    private String bookingId;
    private String flightNumber;
}
