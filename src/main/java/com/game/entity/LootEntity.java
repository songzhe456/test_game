package com.game.entity;

import com.game.server.GameRoll;

public class LootEntity extends Entity {
    public LootEntity(double health, String id) {
        super(GameRoll.getEntities().size(), EntityType.LOOT, health, id);
    }

    @Override
    public void attack(Entity target, double critValue) {
        super.attack(target, critValue);
    }
}
