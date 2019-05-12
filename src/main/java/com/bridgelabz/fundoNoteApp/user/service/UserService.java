package com.bridgelabz.fundoNoteApp.user.service;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.fundoNoteApp.user.model.User;

public interface UserService {
	public String login(User user);

	public User update(HttpServletRequest requst, User user);


//	public void deleteUser(long id);

	public User userRegistration(User user);
	// public String EncryptedPassword(User user);

	public String encryptedPassword(User user);

	// public String jwtToken(String secretKey, String subject);
	//public String jwtToken();

	public int tokenVerification(String token);

	//public String jwtToken(String secretKey, Long Id);


	//String jwtToken(String secretKey, String id);

	String jwtToken(String subject, long id);


}