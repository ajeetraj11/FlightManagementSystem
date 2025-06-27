package com.capge.casestudy.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long bookingId;
    private Double amount;
    private String userName;
    private String pnr;
    private String userEmail;
    private String userId;
}
