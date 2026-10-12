package com.game.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NullEntity extends Entity {
    private static final Logger LOGGER = LoggerFactory.getLogger(NullEntity.class);

    public NullEntity(double health, String id) {
        super(2, EntityType.NULL, health, id);
    }

    @Override
    public void attack(Entity target, double critValue) {
        doAttack(target,critValue,(t,c) -> {
            if (t.getHealth() > 0) {
                String originId = t.getId();
                String nullEntityType = "被篡改";
                t.setId(nullEntityType);
                LOGGER.info("{}把{}的名字{}篡改为{}", getId(), originId, originId, t.getId());
            } else {
                LOGGER.warn("目标{}已死亡", t);
            }
        });
    }
}
