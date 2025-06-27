package com.capge.casestudy.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StripeSessionResponse {
    private String sessionId;
    private String sessionUrl;
    private Long paymentId;
    private String pnr;
    private String userName;
    private Double amount;
    private String status;
    private Long bookingId;

}
