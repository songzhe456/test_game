package com.test.entity;

import com.test.func.Crit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KFCEntity extends Entity{
    Logger logger = LoggerFactory.getLogger(KFCEntity.class);
    private final double damage = 30;
    private final double attackCrit = new Crit().getCrit(damage,getCritValue());
    public KFCEntity(double health, String id) {
        super(EntityType.KFC,health, id);
    }

    @Override
    public void attack(Entity target, double health) {
        logger.info("肯德基攻击！");
        logger.info("{}攻击了{}造成了{}点血量", getId(), target.getId(), Math.round(attackCrit * 10) / 10.0);
        target.setHealth(target.getHealth() - attackCrit);
    }

    public double getAttackCrit() {
        return attackCrit;
    }
}
