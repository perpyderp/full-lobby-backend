package com.perp.fulllobby.exception;

public class FriendshipDoesNotExistException extends RuntimeException {

    public FriendshipDoesNotExistException() {
        super("The friendship does not exist");
    }
    
}
