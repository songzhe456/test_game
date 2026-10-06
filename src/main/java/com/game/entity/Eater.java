package com.game.entity;

import com.game.item.Foods;
import com.game.item.Item;
import com.game.server.GameRoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public abstract class Eater extends Entity implements CanEatFood{
    private static final Logger LOGGER = LoggerFactory.getLogger(Eater.class);

    public Eater(int index, EntityType type, double health, String id) {
        super(index,type, health, id);
    }

    public void selectFoodThenEat(String foodName) {
        for(Item item: GameRoll.getItems()) {
            try {
                if (!(item instanceof Foods)) {
                    continue;
                }
                if (Objects.equals(item.name, foodName)) {
                    eat((Foods) item, this);
                }
            } catch (Exception e) {
                LOGGER.warn("{}食用动作被取消，原因：",this.getId(),e);
                return;
            }
        }
    }

    @Override
    public void eat(Foods food, Entity entity) {
        CanEatFood.super.eat(food, entity);
    }
}
