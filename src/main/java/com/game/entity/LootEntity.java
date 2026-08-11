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
        if (target.getHealth() > 0) {
            logger.info("{}攻击了{}造成了{}点血量", getId(), target.getId(), Math.round(attackCrit * 10) / 10.0);
            target.setHealth(target.getHealth() - attackCrit);
        }
        else {
            logger.warn("目标{}已死亡",target);
        }
    }

}
