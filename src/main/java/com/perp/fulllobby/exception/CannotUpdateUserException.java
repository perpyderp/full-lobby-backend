package com.perp.fulllobby.exception;

public class CannotUpdateUserException extends RuntimeException{
    public CannotUpdateUserException() {
        super("There was an error updating user");
    }
}
