package com.iFound;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	

	    @GetMapping("/home")
	    public String home() {
	        return "homes"; // this will render src/main/resources/templates/home.html
	    }
	
}
