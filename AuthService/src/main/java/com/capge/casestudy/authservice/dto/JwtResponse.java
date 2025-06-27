package com.capge.casestudy.authservice.dto;

public class JwtResponse {
    private String token;
    private String role;

    public JwtResponse(String token , String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
