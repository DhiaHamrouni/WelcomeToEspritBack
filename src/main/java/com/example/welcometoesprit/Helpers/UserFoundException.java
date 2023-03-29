package com.example.welcometoesprit.Helpers;

public class UserFoundException extends Exception{

	
	
	
	public UserFoundException() {
		
		super("username déja existe pas dans la base de donnée ");
		
	}
	

	/*public UserNotFoundException(String message, Throwable cause) {
		
		super(message, cause);
		
	}*/
	
	
	
	public UserFoundException(String message) {
		
			super(message);
		
	}
	
	
	
}
