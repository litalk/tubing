package com.tubing.common;

public class TubingException extends RuntimeException {

    private static final long serialVersionUID = -1675764702092673124L;

    public TubingException(String message) {

        super(message);
    }

    public TubingException(String message, Throwable thrown) {
        
        super(message, thrown);
    }
}
