package com.game.place;

import com.game.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArrayList;

public class Place {
    private static final Logger LOGGER = LoggerFactory.getLogger(Place.class);
    private final Places placeType;
    private final CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<>();
    public Place(Places placeType){
        this.placeType = placeType;
    }

    public void addEntity(Entity entity){
        LOGGER.info("{}进入了{}",entity,placeType);
        entities.add(entity);
        LOGGER.info("{}现在有以下实体：{}",placeType,entities);
    }

    public void removeEntity(Entity entity){
        entities.remove(entity);
    }

    public CopyOnWriteArrayList<Entity> getEntities() {
        return entities;
    }
}
