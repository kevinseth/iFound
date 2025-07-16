//package com.iFound.config;
//
//
//import com.iFound.utility.JwtUtility;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Override
//    public void onAuthenticationSuccess(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Authentication authentication) throws IOException, ServletException {
//
//        // Generate JWT token after successful login
//        String token = JwtUtility.generateToken(authentication);
//
//        // Store JWT token in session under "jwtToken"
//        HttpSession session = request.getSession();
//        session.setAttribute("jwtToken", token);
//
//        // You can optionally log or do other stuff here
//        System.out.println("Login successful, JWT generated and stored in session: " + token);
//
//        // Continue with default behavior (redirect to home page or wherever)
//        response.sendRedirect("/home");
//    }
//}
//
