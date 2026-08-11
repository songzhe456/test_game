package com.game.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NullEntity extends Entity{
    private static Logger logger = LoggerFactory.getLogger(NullEntity.class);
    public NullEntity(double health, String id) {
        super(2,EntityType.NULL,health, id);
    }

    @Override
    public void attack(Entity target, double health) {
        if (target.getHealth() > 0) {
            String originId = target.getId();
            String nullEntityType = "被篡改";
            target.setId(nullEntityType);
            logger.info("{}把{}的名字{}篡改为{}", getId(), originId, originId, target.getId());
        }
        else {
            logger.warn("目标{}已死亡",target);
        }
    }
}
