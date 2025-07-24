package com.iFound;

import com.iFound.dto.UserRegistrationDto;
import com.iFound.model.User;
import com.iFound.repository.UserRepository;
import com.iFound.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @Autowired //to use it more time
    private UserRepository userRepository;
    
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @GetMapping("/register-form")
    public String redirectToRegister() {
        return "redirect:/register";
    }

    @PostMapping("/register")
    @ResponseBody
    public User registerViaApi(@RequestBody UserRegistrationDto dto) {
        return service.register(dto);
    }

    @PostMapping("/register-form")
    public String registerViaForm(@ModelAttribute UserRegistrationDto dto, Model model, HttpServletRequest request) {
        try {
            service.register(dto);
            model.addAttribute("success", "User registered successfully!");
            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", dto);  // refill form with submitted data
            return "register";
        }
    }

    @GetMapping("/user-management")
    public String checkStatusPage(Model model, HttpServletRequest request, Authentication authentication) {
        String token = (String) request.getSession().getAttribute("jwtToken");
        String sessionId = request.getSession().getId();

        model.addAttribute("token", token);
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("user", new User());

        // Add logged-in username if authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }

        if (token != null && !token.isEmpty()) {
            List<User> users = userRepository.findAll();
            model.addAttribute("users", users);
        }

        return "user-management";
    }
}
