package com.capge.casestudy.authservice.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String userEmail;
    private String password;

}
