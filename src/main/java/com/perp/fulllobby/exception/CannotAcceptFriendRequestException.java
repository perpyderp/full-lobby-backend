package com.perp.fulllobby.exception;

public class CannotAcceptFriendRequestException extends RuntimeException {
    
    public CannotAcceptFriendRequestException() {
        super("Cannot accept the friend request.");
    }
}
