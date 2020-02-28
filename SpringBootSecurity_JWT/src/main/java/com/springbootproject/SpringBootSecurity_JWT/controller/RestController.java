package com.springbootproject.SpringBootSecurity_JWT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;

import com.springbootproject.SpringBootSecurity_JWT.dao.UserDAO;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	UserDAO userDAO;

	@GetMapping("/")
	public String test(Authentication auth) {
		//show the email of the authenticated user.
		return "Your email is: "+auth.getName();
	}
}
