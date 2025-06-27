package com.capge.casestudy.userservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secretKey;
    public String extractEmail(String token) {
        String cleanedToken = token.replace("Bearer", "").trim();
        return Jwts.parserBuilder()
                .setSigningKey(Decoders.BASE64.decode(secretKey.trim()))
                .build()
                .parseClaimsJws(cleanedToken)
                .getBody()
                .get("email" , String.class);
    }
}