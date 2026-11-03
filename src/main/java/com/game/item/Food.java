package com.game.item;

import com.game.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Food extends Item {
    private static final Logger LOGGER = LoggerFactory.getLogger(Food.class);
    private final double healthResume;

    public Food(String name, double healthResume) {
        super(ItemType.FOODS, name);
        this.healthResume = healthResume;

    }

    public void eat(Entity entity) {
        entity.setHealth(entity.getHealth() + healthResume);
        LOGGER.info("{}吃了{}回了{}血量", entity.getId(), this.name, healthResume);
    }

    public enum FoodType {
        APPLE,
        GREAT_APPLE,
        COKE
    }

    public static Food create(FoodType type, String name) {
        return switch (type) {
            case APPLE -> new Apple(name);
            case GREAT_APPLE -> new GreatApple(name);
            case COKE -> new Coke(name);
            default -> new Apple("食物创建失败");
        };
    }
}
