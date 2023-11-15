package com.perp.fulllobby.exception;

public class ImageNotFoundException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ImageNotFoundException() {
        super("This image could not be found");
    }

    public ImageNotFoundException(String filename) {
        super("The image with filename: " + filename + " could not be found.");
    }
}
