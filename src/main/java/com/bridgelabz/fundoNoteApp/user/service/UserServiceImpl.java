package com.bridgelabz.fundoNoteApp.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoNoteApp.user.model.User;
import com.bridgelabz.fundoNoteApp.user.repository.UserRepository;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	public UserRepository userRep;

	@Override
	public String login(User user) {
		String password = encryptedPassword(user);
//		List<User> usrlst = userRep.findByEmailAndPassword(user.getEmail(), user.getPassword());
		List<User> userList = userRep.findByidAndPassword(user.getId(), password);

		System.out.println("SIZE : " + userList.size());

		if (userList.size() > 0 && userList != null) {
			System.out.println("Sucessful login");
			return "Welcome  " + userList.get(0).getName() + "   Jwt Token is :"
					+ jwtToken("secretKey", user.getName());
		} else {
			System.out.println("wrong Id or password");
			return "wrong id or password";
		}
	}

	@Override
	public User userRegistration(User user) {
		user.setPassword(encryptedPassword(user));
		return userRep.save(user);
	}

	@Override
	public String encryptedPassword(User user) {
		String passwordToHash = user.getPassword();
		System.out.println("password: " + passwordToHash);
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(passwordToHash.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println("generated password :" + generatedPassword);
		/*
		 * String password = user.getPassword(); System.out.println("password :"
		 * +password );
		 * 
		 * BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); String
		 * hashedPassword = passwordEncoder.encode(password); String
		 * pass=passwordEncoder.encode(toString());
		 * System.out.println("pass is :"+pass); System.out.println(hashedPassword);
		 * return hashedPassword;
		 */
		return generatedPassword;
	}

	public String jwtToken(String secretKey, String subject) {

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(now).signWith(SignatureAlgorithm.HS256,
				secretKey);
		System.out.println("jwt token :" + builder.compact());
		return builder.compact();

	}

}
