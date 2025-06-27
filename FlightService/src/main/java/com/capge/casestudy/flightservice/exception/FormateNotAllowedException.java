package com.capge.casestudy.flightservice.exception;

public class FormateNotAllowedException extends  Exception{
    public FormateNotAllowedException(){
	   super("Format Mismatch Found : Please provide data correctly");
    }

    public FormateNotAllowedException(String message){
	   super(message);
    }
}
