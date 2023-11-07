package com.perp.fulllobby.exception;

public class CannotCreatePostException extends RuntimeException{

    public CannotCreatePostException() {
        super("Couldn't create post");
    }
    
}
