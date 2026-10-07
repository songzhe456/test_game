package com.game.entity;

import com.game.func.Crit;
import com.game.item.Foods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KFCEntity extends Eater{
    private static final Logger LOGGER = LoggerFactory.getLogger(KFCEntity.class);
    public KFCEntity(double health, String id) {
        super(3,EntityType.KFC,health, id);
        setDamage(30);
    }

    @Override
    public void attack(Entity target, double attackCrit) {
        if (target.getHealth() > 0) {
            LOGGER.info("肯德基攻击！");
            super.attack(target, getCrit().getCrit(getDamage(),attackCrit));
            selectFoodThenEat("coke",this);
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
