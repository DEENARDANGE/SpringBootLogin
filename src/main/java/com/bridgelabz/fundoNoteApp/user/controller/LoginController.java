package com.bridgelabz.fundoNoteApp.user.controller;

import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoNoteApp.user.model.User;
import com.bridgelabz.fundoNoteApp.user.service.UserService;

@RestController
public class LoginController {
	@Autowired
	UserService userService;

	@Autowired
	private JavaMailSender sender;

//SEND EMAIL
	@RequestMapping("/sendMail")
	public String sendMail(@RequestBody User user) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(user.getEmail());
			helper.setText("Greetings :)");
			helper.setSubject("Mail From Spring Boot");
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String geteUserByLogin(@RequestBody User user, HttpServletRequest reuest, HttpServletResponse response) {

		String token = userService.login(user);
		response.setHeader("token", token);

		System.out.println("token is ********* :" + token);
		return "user->" + token;
	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.PUT)
	public void updateuser(@RequestBody User user, HttpServletRequest request) {
		System.out.println("I am  token at update method :" + request.getHeader("token"));
		userService.update(request.getHeader("token"), user);
	}

	@RequestMapping(value = "/deleteuser", method = RequestMethod.DELETE)
	public void deleteuser(HttpServletRequest request) {

		System.out.println("I am  token at delete method :" + request.getHeader("token"));
		boolean b = userService.delete(request.getHeader("token"));
		System.out.println("-->" + b);

	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.PUT)
	public void forgotpassword(@RequestBody User user, HttpServletRequest request) {
		User userInfo = userService.getUserInfoByEmail(user.getEmail());

		if (userInfo != null) {
			String token = userService.jwtToken("secretKey", userInfo.getId());

			userService.sendMail(userInfo, request, token);

		}
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.PUT)
	public void resetPassword(@RequestBody User user, HttpServletRequest request) {
		// User userInfo=userService.getUserInfoByEmail(user.getEmail());
		int id = userService.tokenVerification(request.getHeader("token"));

		if (id != 0) {

			Optional<User> userinfo = userService.findById(id);
			User usr = userinfo.get();
			usr.setPassword(user.getPassword());
			userService.update(request.getHeader("token"), usr);
		}

	}

}