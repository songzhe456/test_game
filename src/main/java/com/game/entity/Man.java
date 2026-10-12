package com.game.entity;

import com.game.item.Foods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Man extends Eater {
    private static final Logger LOGGER = LoggerFactory.getLogger(Man.class);

    public Man(double health, String id) {
        super(1, EntityType.MAN, health, id);
        setDamage(50);
    }

    @Override
    public void attack(Entity target, double critValue) {
        doAttack(target,critValue,(t,c) -> {
            if (t.getHealth() > 0) {
                super.attack(t, getCritValue());
                selectFoodThenEat("great apple", this);
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
