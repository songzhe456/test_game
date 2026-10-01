package com.game.entity;

public abstract class Eater extends Entity implements CanEatFood{
    public Eater(int index,EntityType type, double health, String id) {
        super(index,type, health, id);
    }
}
