package com.perp.fulllobby.exception;

public class EmailAlreadyTakenException extends RuntimeException{
    public EmailAlreadyTakenException() {
        super("Email is already in use");
    }
}
