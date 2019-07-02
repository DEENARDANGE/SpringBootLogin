package com.bridgelabz.fundonoteapp.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundonoteapp.user.model.User;
import com.bridgelabz.fundonoteapp.user.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/registration")
	public ResponseEntity<String> createUser(@RequestBody User user, HttpServletRequest request,
			HttpServletResponse response) {

		userService.userRegistration(user, request);
		return new ResponseEntity<>("{Registration Succesfull}", HttpStatus.OK);
	}

}