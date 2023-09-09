package com.perp.fulllobby.exception;

public class IncorrectVerificationCodeException extends RuntimeException {
    
    public IncorrectVerificationCodeException() {
        super("The code didn't pass the user verification code");
    }

}
