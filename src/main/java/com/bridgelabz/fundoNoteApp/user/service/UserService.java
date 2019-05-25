package com.bridgelabz.fundoNoteApp.user.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.fundoNoteApp.user.model.LoginRequest;
import com.bridgelabz.fundoNoteApp.user.model.User;

public interface UserService {
	public String login(LoginRequest loginReq);

	public User update(String token, User user);

	public User userRegistration(User user, HttpServletRequest request);

	public boolean delete(String token);

	public String sendMail(User user, String url, String subject);

	public List<User> getUser();

	// String login(LoginRequest loginReq);

}
