package com.test.entity;

public class NullEntity extends Entity{
    private static final EntityType nullEntity = EntityType.NULL;
    public NullEntity(double health, String id) {
        super(EntityType.NULL,health, id);
    }

    @Override
    public void attack(Entity target, double health) {

    }
}
