package com.game.entity;

import com.game.func.Crit;
import com.game.item.Foods;
import com.game.item.Item;
import com.game.server.GameRoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

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
            for(Item item:GameRoll.getItems()) {
                try {
                    if (!(item instanceof Foods)) {
                        continue;
                    }
                    if (Objects.equals(item.name, "coke")) {
                        eat((Foods) item, this);
                    }
                } catch (Exception e) {
                    LOGGER.warn("{}食用动作被取消，原因：",this.getId(),e);
                    return;
                }
            }
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
