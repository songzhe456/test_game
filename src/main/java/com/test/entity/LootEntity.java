package com.test.entity;

import com.test.func.Crit;
import com.test.funny.Flaw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LootEntity extends Entity{
    private static final Logger logger = LoggerFactory.getLogger(LootEntity.class);
    private final double attackCrit = new Crit().getCrit(1,getCritValue());
    public LootEntity(EntityType type, double health, String id) {
        super(type, health, id);
    }

    @Override
    public void attack(Entity target, double health) {
        logger.info("{}攻击了{}造成了{}点血量", getId(), target.getId(), Math.round(attackCrit * 10) / 10.0);
        target.setHealth(target.getHealth() - attackCrit);
    }

    public double getAttackCrit() {
        return attackCrit;
    }
}
