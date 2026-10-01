package com.game.entity;

import com.game.func.Crit;
import com.game.item.Coke;
import com.game.item.Foods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KFCEntity extends Eater{
    private static final Logger LOGGER = LoggerFactory.getLogger(KFCEntity.class);
    private final double damage = 30;
    private final double attackCrit = new Crit().getCrit(damage,getCritValue());
    public KFCEntity(double health, String id) {
        super(3,EntityType.KFC,health, id);
    }

    @Override
    public void attack(Entity target, double attackCrit) {
        if (target.getHealth() > 0) {
            LOGGER.info("肯德基攻击！");
            super.attack(target, attackCrit);
            eat(new Coke("coke"),this);
        }
        else {
            LOGGER.warn("目标{}已死亡",target);
        }
    }

    public double getAttackCrit() {
        return attackCrit;
    }

    @Override
    public void eat(Foods food, Entity entity) {
        super.eat(food, entity);
    }
}
