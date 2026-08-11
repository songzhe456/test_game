package com.game.util;

import com.game.entity.Entity;
import com.game.func.Crit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomPool {
    //常数定义区
    private static final int LOWEST = 0;
    private static final int MAXIMUM = 4;
    private static final int LOWEST_HP = 0;
    private static final String NULL_ENTITY_ID = null;
    private static final String DEFAULT_ENTITY_ID = "default";
    private static final String COW_ENTITY_ID = "cow";
    private static final String MAN_ENTITY_ID = "man";

    private static final Logger logger = LoggerFactory.getLogger(RandomPool.class);
    private static RandomUtil randomUtil = new RandomUtil();
    private static int randomNum = randomUtil.getRandInt(LOWEST,MAXIMUM);
    public static class RandomEntity extends Entity{
        public RandomEntity(double health, String id) {
            super(7,Entity.EntityType.values()[randomNum], health, id);
        }

        //此方法会返回随机实体类型
        public  static EntityType getRandType(){
            return EntityType.values()[randomNum];
        }

        @Override
        public void attack(Entity target, double health) {
            double attackCrit = new Crit().getCrit(getDamage(),getCritValue());
            target.setHealth(target.getHealth() - attackCrit);
            logger.info("{}攻击了{}造成了{}点血量", getId(), target.getId(), Math.round(attackCrit * 10) / 10.0);
        }
    }

    @Deprecated
    public static void generateRandomEntity(){
        switch (randomNum){
            case (0):{
                RandomEntity nullEntity = new RandomEntity(RandomUtil.getRandDouble(LOWEST_HP, 30),NULL_ENTITY_ID);
                break;
            }
            case (1):{
                RandomEntity defaultEntity = new RandomEntity(RandomUtil.getRandDouble(LOWEST_HP, 30),DEFAULT_ENTITY_ID);
                break;
            }
            case (2): {
                RandomEntity cow = new RandomEntity(RandomUtil.getRandDouble(LOWEST_HP, 30), COW_ENTITY_ID);
                break;
            }
            case (3): {
                RandomEntity man = new RandomEntity(RandomUtil.getRandDouble(LOWEST_HP, 30), MAN_ENTITY_ID);
                break;
            }
        }
    }
}
