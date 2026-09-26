package com.game.entity;

import com.game.server.GameRoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LootEntity extends Entity{
    private static Logger logger = LoggerFactory.getLogger(LootEntity.class);

    public LootEntity(EntityType type, double health, String id) {
        super(GameRoll.getEntities().size(),type, health, id);
    }

    @Override
    public void attack(Entity target, double attackCrit) {
        super.attack(target, attackCrit);
    }

}
