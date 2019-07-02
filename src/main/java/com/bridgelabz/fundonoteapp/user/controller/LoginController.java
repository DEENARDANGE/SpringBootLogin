package com.bridgelabz.fundonoteapp.user.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundonoteapp.user.model.LoginRequest;
import com.bridgelabz.fundonoteapp.user.model.User;
import com.bridgelabz.fundonoteapp.user.repository.UserRepository;
import com.bridgelabz.fundonoteapp.user.service.UserService;
import com.bridgelabz.fundonoteapp.util.Utility;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRep;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginReq, HttpServletRequest request,
			HttpServletResponse response) {
		String token = userService.login(loginReq);
		if (token != null) {
			response.setHeader("token", token);
			response.addHeader("Access-Control-Allow-Headers", "*");
			response.addHeader("Access-Control-Expose-Headers", "*");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>("{invalid user}", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update-user")
	public void updateUser(@RequestBody User user, HttpServletRequest request) {
		System.out.println("I am  token at update method :" + request.getHeader("token"));
		userService.update(request.getHeader("token"), user);
	}

	@DeleteMapping("/delete-user")
	public void deleteuser(HttpServletRequest request) {
		System.out.println("I am  token at delete method :" + request.getHeader("token"));
	 userService.delete(request.getHeader("token"));
		//System.out.println("-->" + b);

	}

	@PostMapping("/forgot-password")
	public String forgotpassword(@RequestBody User user, HttpServletRequest request) {
		User userInfo = userRep.getUserByEmail(user.getEmail());

		if (userInfo != null) {
			String token = Utility.jwtToken("secretKey", userInfo.getId());

			StringBuffer requestUrl = request.getRequestURL();
			System.out.println(requestUrl);
			String forgotPasswordUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			forgotPasswordUrl = forgotPasswordUrl + "/reset-password/" + "token=" + token;
			System.out.println(forgotPasswordUrl);
			String subject = "FOR FORGOT PASSWORD";

			userService.sendMail(userInfo, forgotPasswordUrl, subject);
			return "Mail Sent Successfully";
		} else
			return "not sent";
	}

	@PutMapping("/reset-password")
	public void resetPassword(@RequestBody User user, HttpServletRequest request) {
		int id = Utility.tokenVerification(request.getHeader("token"));

		if (id != 0) {

			Optional<User> userinfo = userRep.findById(id);
			User usr = userinfo.get();
			usr.setPassword(user.getPassword());
			userService.update(request.getHeader("token"), usr);
		}

	}

	@PutMapping("/active")
	public void activestatus(HttpServletRequest request) {
		int id = Utility.tokenVerification(request.getHeader("token"));

		if (id != 0) {

			Optional<User> userinfo = userRep.findById(id);
			User usr = userinfo.get();
			usr.setStatus("1");
			userService.update(request.getHeader("token"), usr);
		}
	}

	@GetMapping("/activestatus/{token}")
	public String activestatus(@PathVariable String token, HttpServletRequest request) {
		int id = Utility.tokenVerification(token);

		String statusMsg = "User Activation Failed";

		if (id != 0) {

			Optional<User> userinfo = userRep.findById(id);
			User usr = userinfo.get();
			usr.setStatus("1");
			userService.update(request.getHeader("token"), usr);
			statusMsg = "User Activated Successfully";
			return statusMsg;
		}

		return statusMsg;
	}

	@GetMapping("/get-user")
	public List<User> getUser(HttpServletRequest request) {

		return userService.getUser();
	}
}
