package com.game.item;

import com.game.entity.Entity;

public class GreatApple extends Apple{
    private static final double HEALTH_RESUME = Double.NaN;
    public GreatApple(String name) {
        super(name);
    }

    @Override
    public void eat(Entity entity) {
        entity.setHealth(entity.getHealth() + HEALTH_RESUME);
        logger.info("{}吃了{}回了{}血量",entity.getId(),this.name,HEALTH_RESUME);
    }
}
