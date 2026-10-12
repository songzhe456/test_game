package com.game.entity;

import com.game.item.Foods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mouse extends Eater {
    private static final Logger LOGGER = LoggerFactory.getLogger(Mouse.class);

    public Mouse(double health, String id) {
        super(4, EntityType.MOUSE, health, id);
    }

    @Override
    public void attack(Entity target, double critValue) {
        doAttack(target,critValue,(t,c) -> {
            if (this.getHealth() > 0 && t != this) {
                setHealth(getHealth() - t.getCritValue());
                LOGGER.info("{}被{}欺负了", getId(), t.getId());
                try {
                    selectFoodThenEat("great apple", t);//老鼠爱吃的大苹果会被抢走
                } catch (NullPointerException npe) {
                    LOGGER.info("{}的食物被抢走了,但他为空所以被抢失败：", this, npe);
                }
            } else if (t == this) {
                LOGGER.warn("老鼠不敢打自己");
            } else {
                LOGGER.warn("自身{}已死亡", this);
            }
        });
    }

    @Override
    public void eat(Foods food, Entity entity) {
        super.eat(food, entity);
        LOGGER.info("{}的食物被抢走了", this);
    }
}
