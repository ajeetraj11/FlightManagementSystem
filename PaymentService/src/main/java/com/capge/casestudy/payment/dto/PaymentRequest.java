package com.capge.casestudy.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long bookingId;
    private Double amount;
    private String pnr;
    private String userName;
    private String userEmail;
    private String userId;

}
