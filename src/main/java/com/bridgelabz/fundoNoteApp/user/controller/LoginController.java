package com.bridgelabz.fundoNoteApp.user.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoNoteApp.user.model.LoginRequest;
import com.bridgelabz.fundoNoteApp.user.model.User;
import com.bridgelabz.fundoNoteApp.user.repository.UserRepository;
import com.bridgelabz.fundoNoteApp.user.service.UserService;
import com.bridgelabz.fundoNoteApp.util.Utility;

@RestController
@RequestMapping("/")
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
//		response.setHeader("token", token);
		if (token != null) {
			response.setHeader("token", token);
			return new ResponseEntity<>(token, HttpStatus.OK);
		}
		else {
		return new ResponseEntity<>("{invalid user}", HttpStatus.BAD_REQUEST);
		}
	}

//	@PostMapping("/login")
//	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
//		String token = userService.login(loginRequest);
//		//String token = loginRequest.getId();
//		if (mayBeUser.isPresent()) {
//			response.setHeader("token", token);
//
//			return new ResponseEntity<>("{}", HttpStatus.OK);
//		}
//		return new ResponseEntity<>("invalid user", HttpStatus.BAD_REQUEST);
//	}

	@PutMapping("/user")
	public void updateUser(@RequestBody User user, HttpServletRequest request) {
		System.out.println("I am  token at update method :" + request.getHeader("token"));
		userService.update(request.getHeader("token"), user);
	}

	@DeleteMapping("/user")
	public void deleteuser(HttpServletRequest request) {
		System.out.println("I am  token at delete method :" + request.getHeader("token"));
		boolean b = userService.delete(request.getHeader("token"));
		System.out.println("-->" + b);

	}

	// @RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	@PostMapping("/password")
	public String forgotpassword(@RequestBody User user, HttpServletRequest request) {
		User userInfo = userRep.getUserByEmail(user.getEmail());

		if (userInfo != null) {
			String token = Utility.jwtToken("secretKey", userInfo.getId());

			StringBuffer requestUrl = request.getRequestURL();
			System.out.println(requestUrl);
			String forgotPasswordUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			forgotPasswordUrl = forgotPasswordUrl + "/resetpassword/" + "token=" + token;
			System.out.println(forgotPasswordUrl);
			String subject = "FOR FORGOT PASSWORD";

			userService.sendMail(userInfo, forgotPasswordUrl, subject);
			return "Mail Sent Successfully";
		} else
			return "not sent";
	}

	// @RequestMapping(value = "/resetpassword", method = RequestMethod.PUT)
	@PutMapping("/resetPassword")
	public void resetPassword(@RequestBody User user, HttpServletRequest request) {
		// User userInfo=userService.getUserInfoByEmail(user.getEmail());
		int id = Utility.tokenVerification(request.getHeader("token"));

		if (id != 0) {

			Optional<User> userinfo = userRep.findById(id);
			User usr = userinfo.get();
			usr.setPassword(user.getPassword());
			userService.update(request.getHeader("token"), usr);
		}

	}

	// @RequestMapping(value = "/activestatus", method = RequestMethod.PUT)
	@PutMapping("/active")
	public void activestatus(HttpServletRequest request) {
		// User userInfo=userService.getUserInfoByEmail(user.getEmail());
		int id = Utility.tokenVerification(request.getHeader("token"));

		if (id != 0) {

			Optional<User> userinfo = userRep.findById(id);
			User usr = userinfo.get();
			usr.setStatus("1");
			userService.update(request.getHeader("token"), usr);
		}
	}

	// @RequestMapping(value = "/get", method = RequestMethod.GET)
	@GetMapping("/users")
	public List<User> getUser(HttpServletRequest request) {

		return userService.getUser();
	}
}
