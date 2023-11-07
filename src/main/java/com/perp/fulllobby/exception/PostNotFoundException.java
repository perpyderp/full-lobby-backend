package com.perp.fulllobby.exception;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException() {
        super("Post could not be found");
    }
    
}
