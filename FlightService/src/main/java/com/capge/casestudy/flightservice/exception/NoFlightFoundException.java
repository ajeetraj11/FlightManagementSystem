package com.capge.casestudy.flightservice.exception;

public class NoFlightFoundException extends Exception{
    public NoFlightFoundException(){
	   super("No Such Flight Found");
    }

    public NoFlightFoundException(String message){
	   super(message);
    }
}
