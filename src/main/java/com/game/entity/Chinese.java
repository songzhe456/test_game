package com.game.entity;

import com.game.entity.func.BodyPart;
import com.game.entity.func.Direction;
import com.game.server.GameRoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Chinese extends Eater{
    private static final Logger LOGGER = LoggerFactory.getLogger(Chinese.class);
    public Chinese(double health, String id) {
        super(6,EntityType.CHINESE,health, id);
    }

    @Override
    public void attack(Entity target, double critValue) {
        super.attack(target,critValue);
        GameRoll.entityMove(this, Direction.Directions.UP,this.getId(), BodyPart.HEAD);
        LOGGER.info("{}能飞",getId());
    }
}
