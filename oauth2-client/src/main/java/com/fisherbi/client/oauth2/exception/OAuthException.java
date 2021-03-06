package com.fisherbi.client.oauth2.exception;

public class OAuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;


    public OAuthException(String message, Exception e) {
        super(message, e);
    }


    public OAuthException(String message) {
        super(message, null);
    }


    public OAuthException(Exception e) {
        super(e);
    }
}
