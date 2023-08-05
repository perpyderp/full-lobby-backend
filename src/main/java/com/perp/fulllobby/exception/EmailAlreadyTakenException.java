package com.perp.fulllobby.exception;

public class EmailAlreadyTakenException extends RuntimeException {
    
    public EmailAlreadyTakenException() {
        super("The email provided is already taken");
    }

}
