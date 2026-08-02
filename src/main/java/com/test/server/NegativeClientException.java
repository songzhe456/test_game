package com.test.server;

public class NegativeClientException extends RuntimeException {
    public NegativeClientException(String message) {
        super(message);
    }
}
