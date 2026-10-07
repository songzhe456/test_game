package com.game.entity;

import com.game.func.Crit;
import com.game.item.Foods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cow extends Eater{
    private final double damage = 20;
    private static final Logger LOGGER = LoggerFactory.getLogger(Cow.class);
    public Cow(double health, String id) {
        super(0,EntityType.COW,health, id);
    }
    private final Crit crit = new Crit();
    @Override
    public void attack(Entity target, double attackCrit) {
        if(target.getHealth() > 0) {
            super.attack(target, crit.getCrit(getDamage(),attackCrit));
            selectFoodThenEat("apple",this);
        }
        else {
            LOGGER.warn("目标{}已死亡",target);
        }
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public void eat(Foods food, Entity entity) {
        super.eat(food, entity);
    }
}
