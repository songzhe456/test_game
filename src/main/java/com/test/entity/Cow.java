package com.test.entity;

import com.test.func.Crit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cow extends Entity{
    private double damage = 20;
    private static final EntityType cow = EntityType.COW;
    private static final Logger logger = LoggerFactory.getLogger(Cow.class);
    public Cow(double health, String id) {
        super(EntityType.COW,health, id);
    }
    private final double attackCrit = new Crit().getCrit(damage,getCritValue());;
    @Override
    public void attack(Entity target, double health) {
        logger.info("{}攻击了{}造成了{}点血量", getId(), target.getId(), Math.round(attackCrit * 10) / 10.0);
        target.setHealth(target.getHealth() - attackCrit);
    }

    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getAttackCrit() {
        return attackCrit;
    }
}
