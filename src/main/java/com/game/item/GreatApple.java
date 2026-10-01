package com.game.item;

import com.game.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreatApple extends Apple{
    private static final double HEALTH_RESUME = Double.NaN;
    private static final Logger LOGGER = LoggerFactory.getLogger(GreatApple.class);

    public GreatApple(String name) {
        super(name);
    }

    @Override
    public void eat(Entity entity) {
        entity.setHealth(entity.getHealth() + HEALTH_RESUME);
        LOGGER.info("{}吃了{}回了{}血量",entity.getId(),this.name,HEALTH_RESUME);
    }
}
