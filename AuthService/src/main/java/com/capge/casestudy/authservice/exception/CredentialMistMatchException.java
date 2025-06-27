package com.capge.casestudy.authservice.exception;

public class CredentialMistMatchException extends RuntimeException{
    public CredentialMistMatchException(){
	   super("Credential Not Exists In the System .. try Again");
    }

    public CredentialMistMatchException(String message){
	   super(message);
    }
}
