package com.game.item;

import com.game.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Foods extends Item{
    private static final Logger LOGGER = LoggerFactory.getLogger(Foods.class);
    private static double healthResume;
    public Foods(String name, double healthResume) {
        super(ItemType.FOODS, name);
        Foods.healthResume = healthResume;

    }
    public void eat(Entity entity){
        entity.setHealth(entity.getHealth() + healthResume);
        LOGGER.info("{}吃了{}回了{}血量",entity.getId(),this.name,healthResume);
    }

    public static Foods create(String type, String name) {
        if(Objects.equals(type,"apple")){
            return new Apple(name);
        }
        else {
            return null;
        }
    }
}
