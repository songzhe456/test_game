package com.game.server;

@SuppressWarnings("unused")
public class NegativeClientException extends RuntimeException {
    public NegativeClientException(String message) {
        super(message);
    }

    public NegativeClientException(Throwable e) {
        super(e);
    }
}
