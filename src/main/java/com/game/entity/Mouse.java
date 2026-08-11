package com.game.entity;

import com.game.item.Foods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mouse extends Eater{
    private static Logger logger = LoggerFactory.getLogger(Mouse.class);
    public Mouse(double health, String id) {
        super(4,EntityType.MOUSE, health, id);
    }

    @Override
    public void attack(Entity target, double attackCrit) {
        if (this.getHealth() > 0 && target != this) {
            setHealth(getHealth() - target.getDamage());
            logger.info("{}被{}欺负了", getId(), target.getId());
            try {
                eat(null, target);
            } catch (Exception e) {
                logger.info("{}的食物被抢走了,但他为空所以被抢失败",this);
            }
        } else if (target == this) {
            logger.warn("老鼠不敢打自己");
        } else {
            logger.warn("自身{}已死亡",this);
        }
    }

    @Override
    public void eat(Foods food, Entity entity) {
        super.eat(food, entity);
        logger.info("{}的食物被抢走了,虽然是空气",this);
    }
}
