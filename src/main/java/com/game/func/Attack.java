package com.game.func;

import com.game.entity.Entity;
public interface Attack {
    void attack(Entity target, double health) throws InterruptedException;
}
