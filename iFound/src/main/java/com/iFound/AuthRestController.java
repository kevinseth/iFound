package com.iFound;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.iFound.utility.JwtUtility;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> creds, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    creds.get("username"),
                    creds.get("password")
                )
            );

            // 🛡️ Simulate or generate a token (for now just a dummy string)
            String token = "dummy-jwt-token-123456"; // Replace with real token later if needed
            String sessionId = request.getSession().getId(); // 🔐 get session ID
            // ✅ Store token in session
            request.getSession().setAttribute("token", token);

            return ResponseEntity.ok(Map.of("Session",sessionId,"message", "Login successful", "token", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid username or password"));
        }
    }



}