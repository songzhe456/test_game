package com.game.func;

import com.game.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fight {
    private static final Logger LOGGER = LoggerFactory.getLogger(Fight.class);
    public void fight(Entity attacker, Entity target) {
        if(attacker != null) {
            if(target != null) {
                attacker.attack(target, attacker.getCritValue());
                LOGGER.info("{}造成了额外暴击{}", attacker.getId(), attacker.getLiteralCritValue());
            }
            else {
                LOGGER.warn("目标不存在");
            }
        }
        else {
            LOGGER.warn("攻击者不存在");
        }
    }
}
