package com.iFound;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

//	@GetMapping("/")
//	public String greet(HttpServletRequest request) {
//		return "Hello!"+ request.getSession().getId();
//	}
	

	    @GetMapping("/index")
	    public String showLoginPage() {
	        return "index"; // loads index.html
	    }
	

}