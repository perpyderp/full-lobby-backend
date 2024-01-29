package com.perp.fulllobby.exception;

public class CannotFindLikeException extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CannotFindLikeException() {
        super("Couldn't find the like");
    }
}
