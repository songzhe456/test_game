package com.game.func;

import com.game.entity.Entity;

@FunctionalInterface
public interface CanAttack {
    void attack(Entity target, double health);
}
