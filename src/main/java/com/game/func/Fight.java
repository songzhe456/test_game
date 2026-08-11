package com.game.func;

import com.game.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fight {
    Logger logger = LoggerFactory.getLogger(Fight.class);
    public void fight(Entity attacker, Entity target) {
        if(attacker != null) {
            if(target != null) {
                attacker.setCritValue(10);
                attacker.attack(target, target.getCritValue());
                logger.info("{}造成了暴击{}", attacker.getId(), attacker.getCritValue());
            }
            else {
                logger.warn("目标不存在");
            }
        }
        else {
            logger.warn("攻击者不存在");
        }
    }
}
