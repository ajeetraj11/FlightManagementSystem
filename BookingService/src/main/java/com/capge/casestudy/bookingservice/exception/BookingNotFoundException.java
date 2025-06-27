package com.capge.casestudy.bookingservice.exception;

public class BookingNotFoundException extends Exception{
    public BookingNotFoundException(String message){
	   super(message);
    }
}
