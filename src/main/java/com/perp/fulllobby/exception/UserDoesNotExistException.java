package com.perp.fulllobby.exception;

public class UserDoesNotExistException extends RuntimeException{
    
    public UserDoesNotExistException() {
        super("The user does not exist");
    }

}
