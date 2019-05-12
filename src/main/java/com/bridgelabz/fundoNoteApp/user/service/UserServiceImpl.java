package com.bridgelabz.fundoNoteApp.user.service;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bridgelabz.fundoNoteApp.user.model.User;
import com.bridgelabz.fundoNoteApp.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	public UserRepository userRep;
	String secretKey;
	String subject;
	private Long Id;

	@Override
	public String login(User user) {
		String password = encryptedPassword(user);
//		List<User> usrlst = userRep.findByEmailAndPassword(user.getEmail(), user.getPassword());
		List<User> userList = userRep.findByidAndPassword(user.getId(), password);
		System.out.println("idddd :" + user.getId());
		System.out.println("SIZE : " + userList.size());

		if (userList.size() > 0 && userList != null) {
			System.out.println("Sucessful login");

			// System.out.println("varified :"+TokenVerification());

			return jwtToken(password, userList.get(0).getId());
		} else
			System.out.println("wrong Id or password");
		return "wrong id or password";
	}

	// Update
	//@RequestMapping(value = "/user", method = RequestMethod.PUT)
	//public User updateUser(@RequestBody User user, String token) {
	//@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	@Override
	public User update(HttpServletRequest requst, User user)
	{
         String tokenHeader=requst.getHeader("Token");
         
		System.out.println("I am  token at impl update method :" + tokenHeader);
		//int gettingid= tokenVerification(token);
		int userId = tokenVerification(tokenHeader);
		int varifiedId=userId;
		System.out.println("varifiedId : "+varifiedId);
	
		//System.out.println("######gettingid: " + gettingid);
		
		// dao.findbyid()gettingid;
		//dao.save(user);
		Optional<User> maybeUser = userRep.findById(userId);
		User presentUser = maybeUser.map(existingUser -> {
			existingUser.setEmail(user.getEmail());
			existingUser.setPhonenumber(user.getPhonenumber());
			return existingUser;
		}).orElseThrow(() -> new RuntimeException("User Not Found"));
	
		userRep.save(presentUser);
		return  userRep.save(user);
	}

//	// Delete
//	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
//	public void deleteUser(@PathVariable long id) {
//		userRep.deleteById(id);
//	}

	// String token = jwtToken(token, Id);
	// String output = tokenVerification(token);

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

//
//	public String jwtToken() {
//		String id = UUID.randomUUID().toString().replace("-", "");
//		Date now = new Date();
//		Date exp = new Date(System.currentTimeMillis() + (1000 * 30)); // 30 seconds
//
//		String token = Jwts.builder().setId(id).setIssuedAt(now).setNotBefore(now).setExpiration(exp)
//				.signWith(SignatureAlgorithm.HS256, base64SecretBytes).compact();
//		System.out.println("token generation:" + token);
//		return token;
//	}
//
//	@Override
//	public String tokenVerification(String token) {
//		Claims claims = Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(token).getBody();
//		System.out.println("----------------------------");
//		System.out.println("ID: " + claims.getId());
//		System.out.println("Subject: " + claims.getSubject());
//		System.out.println("Issuer: " + claims.getIssuer());
//		System.out.println("Expiration: " + claims.getExpiration());
//		System.out.println("varified token :" + token);
//		return token;
//	}

//
//	String token = jwtToken(secretKey, Id);
//
//	String output = tokenVerification(token);
//

	private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
	private static final byte[] secretBytes = secret.getEncoded();
	private static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);
	@Override
	public String jwtToken(String secretKey, long id) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		JwtBuilder builder = Jwts.builder().
				setSubject(String.valueOf(id))
				.setIssuedAt(now)
				//.setExpiration(now)
				.signWith(SignatureAlgorithm.HS256, base64SecretBytes);
		System.out.println("jwt token :" + builder.compact());
		String token = builder.compact();

		return token;
	}
	@Override
	public int tokenVerification(String token) {
		// This line will throw an exception if it is not a signed JWS (as expected)
		if (StringUtils.isEmpty(token)) {
			// throw error
		}
		Claims claims = Jwts
				.parser()
				.setSigningKey(base64SecretBytes)
				.parseClaimsJws(token)
				.getBody();
		System.out.println("ID******************: " + claims.getSubject());
		
		
//		System.out.println("Subject: " + claims.getSubject());
//		System.out.println("Issuer: " + claims.getIssuer());
//	    System.out.println("Expiration: " + claims.getExpiration());
//		System.out.println("tokenverification :" + token);
		System.out.println("Id is varified :" + claims.getSubject());
		
		return Integer.parseInt(claims.getSubject());
	}
//	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
//	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
//		System.out.println("Updating User " + id);
//
//		User currentUser = userService.findById(id);
//
//		if (currentUser == null) {
//			System.out.println("User with id " + id + " not found");
//			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//		}
//
//	}

	
}
