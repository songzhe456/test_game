package com.game.entity.poster;

public interface Postable {
    void post(String content) throws InterruptedException;
}
