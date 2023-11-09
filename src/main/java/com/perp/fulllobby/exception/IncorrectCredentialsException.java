package com.perp.fulllobby.exception;

public class IncorrectCredentialsException extends RuntimeException {
    
    public IncorrectCredentialsException() {
        super("The username or password entered was incorrect. Please try again");
    }
}
