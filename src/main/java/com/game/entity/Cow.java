package com.game.entity;

import com.game.func.Crit;
import com.game.item.Apple;
import com.game.item.Foods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cow extends Eater{
    private double damage = 20;
    private static final EntityType cow = EntityType.COW;
    private static final Logger logger = LoggerFactory.getLogger(Cow.class);
    public Cow(double health, String id) {
        super(0,EntityType.COW,health, id);
    }
    private final double attackCrit = new Crit().getCrit(damage,getCritValue());;
    @Override
    public void attack(Entity target, double attackCrit) {
        if(target.getHealth() > 0) {
            logger.info("{}攻击了{}造成了{}点血量", getId(), target.getId(), Math.round(attackCrit * 10) / 10.0);
            target.setHealth(target.getHealth() - attackCrit);
            eat(new Apple("apple"),this);
        }
        else {
            logger.warn("目标{}已死亡",target);
            target.setHealth(target.getHealth());
        }
    }

    @Override
    public double getDamage() {
        return damage;
    }

    public double getAttackCrit() {
        return attackCrit;
    }

    @Override
    public void eat(Foods food, Entity entity) {
        super.eat(food, entity);
    }
}
