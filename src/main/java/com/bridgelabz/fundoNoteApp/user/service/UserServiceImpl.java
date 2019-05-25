package com.bridgelabz.fundoNoteApp.user.service;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoNoteApp.user.model.LoginRequest;
import com.bridgelabz.fundoNoteApp.user.model.User;
import com.bridgelabz.fundoNoteApp.user.repository.UserRepository;
import com.bridgelabz.fundoNoteApp.util.Utility;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRep;

	@Autowired
	private JavaMailSender sender;

	@Override
	public String login(LoginRequest loginReq) {
		Optional<User> maybeUser = userRep.findByEmailAndPassword(loginReq.getEmail(),
				Utility.encryptedPassword(loginReq.getPassword()));
		System.out.println(maybeUser);

		if (maybeUser.isPresent()) {
			System.out.println("Sucessful login");
			return Utility.jwtToken(Utility.encryptedPassword(loginReq.getPassword()), maybeUser.get().getId());
		} 
		else
		return null;

	}

	@Override
	public User update(String token, User user) {
		int varifiedUserId = Utility.tokenVerification(token);

		Optional<User> maybeUser = userRep.findById(varifiedUserId);
		User presentUser = maybeUser.map(existingUser -> {
			existingUser.setEmail(user.getEmail() != null ? user.getEmail() : maybeUser.get().getEmail());
			existingUser.setPhonenumber(
					user.getPhonenumber() != 0 ? user.getPhonenumber() : maybeUser.get().getPhonenumber());
			existingUser.setName(user.getName() != null ? user.getName() : maybeUser.get().getName());
			existingUser.setPassword(user.getPassword() != null ? Utility.encryptedPassword(user.getPassword())
					: maybeUser.get().getPassword());
			return existingUser;
		}).orElseThrow(() -> new RuntimeException("User Not Found"));

		return userRep.save(presentUser);
	}

	@Override
	public boolean delete(String token) {
		int varifiedUserId = Utility.tokenVerification(token);
		Optional<User> maybeUser = userRep.findById(varifiedUserId);
		return maybeUser.map(existingUser -> {
			userRep.delete(existingUser);
			return true;
		}).orElseGet(() -> false);
	}

	@Override
	public User userRegistration(User user, HttpServletRequest request) {
		user.setPassword(Utility.encryptedPassword(user.getPassword()));
		User newUser = userRep.save(user);
		// Optional<User> newUser = userRep.findById(user.getId());
		if (newUser != null) {
			System.out.println("Sucessfull reg");
			// Optional<User> maybeUser = userRep.findById(user.getId());
			String tokenGen = Utility.jwtToken("secretKey", newUser.getId());
			User existingUser = newUser;
			StringBuffer requestUrl = request.getRequestURL();
			System.out.println(requestUrl);
			String activationUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			activationUrl = activationUrl + "/activestatus/" + "token=" + tokenGen;
			System.out.println(activationUrl);
			String subject = "User Activation";

			sendMail(existingUser, activationUrl, subject);
			// return "Mail Sent Successfully";
			return existingUser;

		} else {
			System.out.println("Not sucessful reg");
		}
		return null;
		// return user;
	}

	public String sendMail(User user, String urlPattern, String subject) {

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(user.getEmail());
			helper.setText(urlPattern);
			helper.setSubject(subject);
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";

	}

	@Override
	public List<User> getUser() {
		List<User> users = userRep.findAll();
		return users;

	}

}
