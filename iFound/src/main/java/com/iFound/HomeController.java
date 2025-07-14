package com.iFound;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String home(HttpServletRequest request, Model model) {
	    String token = (String) request.getSession().getAttribute("token");
	    String sessionId = request.getSession().getId(); // 🔐 get session ID
	    System.out.println("Session ID: " + request.getSession().getId());
	    System.out.println("Token in session: " + request.getSession().getAttribute("token"));

	    model.addAttribute("token", token);
	    model.addAttribute("sessionId", sessionId); // 👈 added this

	    return "home";
	}

}
