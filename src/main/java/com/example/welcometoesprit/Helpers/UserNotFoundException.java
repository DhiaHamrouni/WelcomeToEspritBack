package com.example.welcometoesprit.Helpers;

public class UserNotFoundException extends Exception{

	
	
	
	public UserNotFoundException() {
		
		super("username déja existe dans la base de donnée ");
		
	}
	

	/*public UserNotFoundException(String message, Throwable cause) {
		
		super(message, cause);
		
	}*/
	
	
	
	public UserNotFoundException(String message) {
		
			super(message);
		
	}
	
	
}
