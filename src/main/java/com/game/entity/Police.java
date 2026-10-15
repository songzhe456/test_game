package com.game.entity;

import com.toolkit.util.ColorUtil;
import com.toolkit.util.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Police extends Entity{
    private static final Logger LOGGER = LoggerFactory.getLogger(Police.class);
    public Police(double health, String id) {
        super(9, EntityType.POLICE, health, id);
    }

    @Override
    public void attack(Entity target, double critValue) {
        super.attack(target, critValue);
        doAttack(target,critValue,(t,c) -> {
            if(MathUtil.isInvalid(target.getHealth())){
                LOGGER.info(ColorUtil.bold(
                        ColorUtil.color(
                                ColorUtil.Color.BRIGHT_RED,
                                ColorUtil.Bg.BLACK,
                                "目标血量非法，加大力度")
                ));
                setDamage(getDamage() * 2);
            };
        });
    }
}
