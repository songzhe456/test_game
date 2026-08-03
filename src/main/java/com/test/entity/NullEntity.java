package com.test.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NullEntity extends Entity{
    String nullEntityType = "被篡改";
    Logger logger = LoggerFactory.getLogger(NullEntity.class);
    public NullEntity(double health, String id) {
        super(EntityType.NULL,health, id);
    }

    @Override
    public void attack(Entity target, double health) {
        String originId = target.getId();
        target.setId(nullEntityType);
        logger.info("{}把{}的名字{}篡改为{}",getId(),originId,originId,target.getId());
    }
}
