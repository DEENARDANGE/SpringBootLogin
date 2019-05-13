package com.bridgelabz.fundoNoteApp.user.service;

import com.bridgelabz.fundoNoteApp.user.model.User;

public interface UserService {
	public String login(User user);

	public User update(String token, User user);

	public User userRegistration(User user);

	public String encryptedPassword(User user);

	public int tokenVerification(String token);

	String jwtToken(String subject, int id);
	
	public boolean delete(String token);

}