package com.capge.casestudy.authservice.exception;

public class UserNotExistsExceptions extends Exception{
    public UserNotExistsExceptions(){
	   super("No Credential Found in System");
    }

    public UserNotExistsExceptions(String message){
	   super(message);
    }
}
