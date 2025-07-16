package com.iFound;

import com.iFound.model.User;
import com.iFound.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String homePage(Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("jwtToken");
        //String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJLZXZpbiIsImlhdCI6MTc1MjY3NzI5NCwiZXhwIjoxNzUyNzYzNjk0fQ.IuV-J8gsc29JODEPg_bZtaSckazSwPLROLMjDiRT7ZRpUHmLmGbN7pCtRo-wTaagHA0IWthL-WHYHky1pWwb9Q";
        String sessionId = request.getSession().getId();

        model.addAttribute("token", token);
        model.addAttribute("sessionId", sessionId);

        // ✅ Only add users if the token is valid (non-null)
        if (token != null && !token.isEmpty()) {
            List<User> users = userRepository.findAll();
            model.addAttribute("users", users);
        }

        return "home";
    }
}