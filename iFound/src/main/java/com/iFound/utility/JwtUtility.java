package com.iFound.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtility {

    private final String SECRET = "superSecretKey1234567890!@#$%";  // Use a strong raw string
    private final long EXPIRATION_TIME = 60 * 60 * 1000; // 1 hour

    private Key getSigningKey() {
        return new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
