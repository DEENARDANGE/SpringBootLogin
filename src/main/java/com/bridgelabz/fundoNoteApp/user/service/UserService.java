package com.bridgelabz.fundoNoteApp.user.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.fundoNoteApp.user.model.User;

public interface UserService {
    public String login(User user);

    public User update(String token, User user);

    public User userRegistration(User user, HttpServletRequest request);

    public String encryptedPassword(User user);

  //  public int tokenVerification(String token);

  //  String jwtToken(String subject, int id);

    public boolean delete(String token);
    
    
    public User getUserInfoByEmail(String email);
    
    public  String sendMail(User user,String url,String subject);

    public Optional<User> findById(int id);

    

}

