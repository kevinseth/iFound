package com.iFound.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtUtility {

    // ✅ Use a fixed, Base64-decoded secret key string (must be at least 64 bytes for HS512)
    private static final String SECRET = "gYkRr7vG8bM2qWpXtEjLwNeQzHbUdXiAjCnKpTrVsYwMzPbQzHgJkLmNoPqRsTuVw"; // 64+ chars
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000); // 1 day expiry

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }
}
