package com.iFound;

import com.iFound.model.User;
import com.iFound.repository.UserRepository;
import com.iFound.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    private final UserRepository userRepository;
    private final UserService userService;

    // ✅ Constructor injection for both UserRepository and UserService
    public HomeController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/")
    public String indexRedirect() {
        return "redirect:/home";
    }

    
    // ✅ GET /home – display home page with token, session ID, user list, and form


    @GetMapping("/home")
    public String homePage(Model model, HttpServletRequest request, Authentication authentication) {
        String token = (String) request.getSession().getAttribute("jwtToken");
        String sessionId = request.getSession().getId();

        model.addAttribute("token", token);
        model.addAttribute("sessionId", sessionId);
        System.out.println("sessionId:" + sessionId);

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("username", username);

            // Fetch logged-in user from DB
            User loggedInUser = userRepository.findByUsername(username); // or findByEmail(username) depending on your login setup
            if (loggedInUser != null) {
                model.addAttribute("position", loggedInUser.getPosition());
            } else {
                model.addAttribute("position", "Unknown");
            }
        }

        model.addAttribute("user", new User());

        if (token != null && !token.isEmpty()) {
            List<User> users = userRepository.findAll();
            model.addAttribute("users", users);
        }

        return "home";
    }

    
//    @GetMapping("/index?logout=true")
//    public String indexPage(Model model, HttpServletRequest request) {
//        String logout = request.getParameter("logout");
//
//        // 👇 Print session ID after logout (still a valid session)
//        String sessionId = request.getSession().getId();
//        System.out.println("Session ID after logout: " + sessionId);
//
//        if ("true".equals(logout)) {
//            model.addAttribute("message", "You have been logged out successfully.");
//        }
//
//        return "index";
//    }


    
//    @GetMapping("/check-status")
//    public String checkStatus(Model model, HttpServletRequest request) {
//        String token = (String) request.getSession().getAttribute("jwtToken");
//        String sessionId = request.getSession().getId();
//
//        model.addAttribute("token", token);
//        model.addAttribute("sessionId", sessionId);
//        model.addAttribute("user", new User());
//
//        if (token != null && !token.isEmpty()) {
//            List<User> users = userRepository.findAll();
//            model.addAttribute("users", users);
//        }
//        
//        // Optional: add users list
//        return "check-status";
//    }
    

//    // ✅ POST /register-form – handles user registration from HTML form
//    @PostMapping("/register-form")
//    public String registerViaForm(@ModelAttribute User user, Model model, HttpServletRequest request) {
//        userService.register(user);
//        model.addAttribute("success", "User registered successfully!");
//        return "redirect:/home";
//    }
    
    
//  @PostMapping("/home")
//  public String createUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
//      // Save the user to the database
//      userRepository.save(user);
//
//      // Redirect to /home to show updated list
//      return "redirect:/home";
//  }

    
}
