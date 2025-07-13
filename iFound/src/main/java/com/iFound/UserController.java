package com.iFound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iFound.model.User;
import com.iFound.service.UserService;

@RestController
public class UserController {
   
	@Autowired
	private UserService service;
	
     @PostMapping("/register")	
       public User register(@RequestBody User user) {
    	  
    	 return service.register(user);
       }
	
	
}
