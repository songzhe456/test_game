package com.game.entity;

import com.game.func.Crit;
import com.game.item.Coke;
import com.game.item.Foods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KFCEntity extends Eater{
    private static Logger logger = LoggerFactory.getLogger(KFCEntity.class);
    private final double damage = 30;
    private final double attackCrit = new Crit().getCrit(damage,getCritValue());
    public KFCEntity(double health, String id) {
        super(3,EntityType.KFC,health, id);
    }

    @Override
    public void attack(Entity target, double attackCrit) {
        if (target.getHealth() > 0) {
            logger.info("肯德基攻击！");
            logger.info("{}攻击了{}造成了{}点血量", getId(), target.getId(), Math.round(attackCrit * 10) / 10.0);
            target.setHealth(target.getHealth() - attackCrit);
            eat(new Coke("coke"),this);
        }
        else {
            logger.warn("目标{}已死亡",target);
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
