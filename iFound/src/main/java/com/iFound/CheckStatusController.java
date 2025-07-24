package com.iFound;


import com.iFound.model.User;

import com.iFound.repository.UserRepository;
import com.iFound.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CheckStatusController {

    private final UserRepository userRepository;
   

    // ✅ Constructor injection for both UserRepository and UserService
    public CheckStatusController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
      
    }

    // ✅ GET /home – display home page with token, session ID, user list, and form
  

    @GetMapping("/check-status")
    public String checkStatusPage(Model model, HttpServletRequest request, Authentication authentication) {
        String token = (String) request.getSession().getAttribute("jwtToken");
        String sessionId = request.getSession().getId();

        model.addAttribute("token", token);
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("user", new User());

        // ✅ Add logged-in username
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }

        if (token != null && !token.isEmpty()) {
            List<User> users = userRepository.findAll();
            model.addAttribute("users", users);
        }

        return "check-status";
    }

}
