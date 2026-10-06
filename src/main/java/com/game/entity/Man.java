package com.game.entity;

import com.game.item.Foods;
import com.game.item.GreatApple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Man extends Eater{
    private static final Logger LOGGER = LoggerFactory.getLogger(Man.class);
    private final double damage = 50;

    public Man(double health, String id) {
        super(1,EntityType.MAN, health, id);
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public void attack(Entity target, double attackCrit) {
        if (target.getHealth() > 0) {
            super.attack(target, attackCrit);
            selectFoodThenEat("great apple",this);
        }
        else {
            LOGGER.warn("目标{}已死亡",target);
        }
    }

    @Override
    public void eat(Foods food, Entity entity) {
        super.eat(food, entity);
    }
}
