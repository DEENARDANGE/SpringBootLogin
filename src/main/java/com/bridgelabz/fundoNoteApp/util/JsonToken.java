package com.bridgelabz.fundoNoteApp.util;

import org.springframework.stereotype.Service;


public interface JsonToken {
	
	 public String jwtToken(String secretKey, int id) ;
	 
	  public int tokenVerification(String token);
}
