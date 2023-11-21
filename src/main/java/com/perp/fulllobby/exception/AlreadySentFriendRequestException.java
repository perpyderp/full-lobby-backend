package com.perp.fulllobby.exception;

public class AlreadySentFriendRequestException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public AlreadySentFriendRequestException() {
        super("A friend request already exists.");
    }

}
