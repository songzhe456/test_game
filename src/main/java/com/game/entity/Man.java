package com.game.entity;

import com.game.item.Foods;
import com.game.item.GreatApple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Man extends Eater{
    private static Logger logger = LoggerFactory.getLogger(Man.class);
    private double damage = 50;

    public Man(double health, String id) {
        super(1,EntityType.MAN, health, id);
    }


    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public void attack(Entity target, double attackCrit) {
        if (target.getHealth() > 0) {
            logger.info("{}攻击了{}造成了{}点血量", getId(), target.getId(), Math.round(attackCrit * 10) / 10.0);
            target.setHealth(target.getHealth() - attackCrit);
            eat(new GreatApple("great apple"),this);
        }
        else {
            logger.warn("目标{}已死亡",target);
        }
    }

    @Override
    public void eat(Foods food, Entity entity) {
        super.eat(food, entity);
    }
}
