package com.game.entity;

import com.game.func.Crit;
import com.game.item.Foods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cow extends Eater{
    private static final Logger LOGGER = LoggerFactory.getLogger(Cow.class);
    public Cow(double health, String id) {
        super(0,EntityType.COW,health, id);
        setDamage(20);
    }
    @Override
    public void attack(Entity target, double attackCrit) {
        if(target.getHealth() > 0) {
            super.attack(target, getCritValue());
            selectFoodThenEat("apple",this);
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
