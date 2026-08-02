package com.test.util;

import com.test.entity.Entity;
import com.test.entity.Man;
import com.test.func.Crit;
import com.test.funny.Flaw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomPool {
    private static final Logger logger = LoggerFactory.getLogger(RandomPool.class);
    private static RandomUtil randomUtil = new RandomUtil();
    private static int randomNum = randomUtil.getRandInt(0,4);
    public static class RandomEntity extends Entity{
        public RandomEntity(EntityType type, double health, String id) {
            super(type, health, id);
        }



        @Override
        public void attack(Entity target, double health) {
            double attackCrit = new Crit().getCrit(getDamage(),getCritValue());
            target.setHealth(target.getHealth() - attackCrit);
            logger.info("{}攻击了{}造成了{}点血量", getId(), target.getId(), Math.round(attackCrit * 10) / 10.0);
        }
    }
    public static void generateRandomEntity(){
        switch (randomNum){
            case (0):{
                RandomEntity nullEntity = new RandomEntity(Entity.EntityType.values()[randomNum], randomUtil.getRandDouble(0, 30),null);
                break;
            }
            case (1):{
                RandomEntity defaultEntity = new RandomEntity(Entity.EntityType.values()[randomNum], randomUtil.getRandDouble(0, 30),"default");
                break;
            }
            case (2): {
                RandomEntity cow = new RandomEntity(Entity.EntityType.values()[randomNum], randomUtil.getRandDouble(0, 30), "cow");
                break;
            }
            case (3): {
                RandomEntity man = new RandomEntity(Entity.EntityType.values()[randomNum], randomUtil.getRandDouble(0, 30), "man");
                break;
            }
        }
    }
}
