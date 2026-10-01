package com.game.entity.func;

import com.game.entity.Entity;
import com.game.server.GameRoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PartFunc {
    private static final Logger LOGGER = LoggerFactory.getLogger(PartFunc.class);
    private Entity boundEntity;
    private BodyPart boundPart;
    private Direction.Directions boundDirection;
    public PartFunc(BodyPart part, Entity entity, Direction.Directions direction){
        bind(part,entity,direction);
        GameRoll.entityMove(boundEntity,boundDirection,boundEntity.getId(),boundPart);
    }

    private void bind(BodyPart part, Entity entity, Direction.Directions direction){
        this.boundEntity = entity;
        this.boundPart = part;
        this.boundDirection = direction;
        LOGGER.info("{}已与{}绑定",part,entity);
    }
}
