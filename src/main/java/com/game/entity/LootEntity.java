package com.game.entity;

import com.game.server.GameRoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LootEntity extends Entity{

    private static final Logger LOGGER = LoggerFactory.getLogger(LootEntity.class);

    public LootEntity(double health, String id) {
        super(GameRoll.getEntities().size(),EntityType.LOOT, health, id);
    }

    @Override
    public void attack(Entity target, double attackCrit) {
        super.attack(target, attackCrit);
    }

}
