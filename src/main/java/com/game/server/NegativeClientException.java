package com.game.server;

public class NegativeClientException extends RuntimeException {
    public NegativeClientException(String message) {
        super(message);
    }
    public NegativeClientException(Throwable e){
        super(e);
    }
}
