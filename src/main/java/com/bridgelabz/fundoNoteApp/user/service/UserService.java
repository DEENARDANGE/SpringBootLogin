package com.bridgelabz.fundoNoteApp.user.service;

import com.bridgelabz.fundoNoteApp.user.model.User;

public interface UserService {
	public String login(User user);

	public User userRegistration(User user);
	//public String EncryptedPassword(User user);

	public String encryptedPassword(User user);


}