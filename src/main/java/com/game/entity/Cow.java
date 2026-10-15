package com.game.entity;

import com.game.item.Foods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cow extends Eater {
    private static final Logger LOGGER = LoggerFactory.getLogger(Cow.class);

    public Cow(double health, String id) {
        super(0, EntityType.COW, health, id);
        setDamage(20);
    }

    @Override
    public void attack(Entity target, double critValue) {
        if (target.getHealth() > 0) {
            super.attack(target, critValue);
        } else {
            LOGGER.warn("目标{}已死亡", target);
        }
        doAttack(target, critValue, (t, c) -> {
            selectFoodThenEat("apple", this);
        });
    }

    @Override
    public void eat(Foods food, Entity entity) {
        super.eat(food, entity);
    }
}
