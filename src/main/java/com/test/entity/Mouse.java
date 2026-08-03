package com.test.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mouse extends Entity{
    Logger logger = LoggerFactory.getLogger(Mouse.class);
    public Mouse(double health, String id) {
        super(EntityType.MOUSE, health, id);
    }

    @Override
    public void attack(Entity target, double health) {
        target.attack(this,this.getHealth());
        logger.info("{}被{}欺负了",getId(),target.getId());
    }
}
