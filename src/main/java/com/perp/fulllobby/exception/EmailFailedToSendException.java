package com.perp.fulllobby.exception;

public class EmailFailedToSendException extends RuntimeException{

    public EmailFailedToSendException() {
        super("The email failed to send");
    }
    
}
