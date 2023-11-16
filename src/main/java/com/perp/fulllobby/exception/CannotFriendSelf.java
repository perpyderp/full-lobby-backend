package com.perp.fulllobby.exception;

public class CannotFriendSelf extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CannotFriendSelf() {
        super("Cannot follow yourself");
    }

}
