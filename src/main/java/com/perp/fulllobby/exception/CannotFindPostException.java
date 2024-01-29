package com.perp.fulllobby.exception;

public class CannotFindPostException extends RuntimeException{

    public CannotFindPostException() {
        super("Post could not be found");
    }
    
}
