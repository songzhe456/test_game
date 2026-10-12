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
        super.attack(target, critValue);
        doAttack(target,critValue,(t,c) -> {
            if (t.getHealth() > 0) {
                selectFoodThenEat("apple", this);
            } else {
                LOGGER.warn("目标{}已死亡", t);
            }
        });
    }

    @Override
    public void eat(Foods food, Entity entity) {
        super.eat(food, entity);
    }
}
