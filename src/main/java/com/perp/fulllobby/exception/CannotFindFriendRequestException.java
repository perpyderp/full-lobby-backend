package com.perp.fulllobby.exception;

public class CannotFindFriendRequestException extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CannotFindFriendRequestException() {
        super("Couldn't find the friend request");
    }
    
}
