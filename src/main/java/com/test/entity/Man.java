package com.test.entity;

import com.test.func.Crit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Man extends Entity{
    private static final Logger logger = LoggerFactory.getLogger(Man.class);
    private double damage = 50;
    private final double attackCrit = new Crit().getCrit(damage,getCritValue());
    public Man(double health, String id) {
        super(EntityType.MAN, health, id);
    }


    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public void setDamage(double damage) {
        this.damage = damage;
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
