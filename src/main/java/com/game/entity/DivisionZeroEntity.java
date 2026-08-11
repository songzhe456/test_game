package com.game.entity;

public class DivisionZeroEntity extends Entity{
    public DivisionZeroEntity(double health, String id) {
        super(5,EntityType.DIVISION_ZERO, health, id);
    }

    @Override
    public void attack(Entity target, double critValue) {
        super.attack(target, 1 / 0);
    }
}
