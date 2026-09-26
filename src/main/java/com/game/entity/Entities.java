package com.game.entity;

import com.game.server.GameRoll;
import com.game.util.RandomPool;
import com.game.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.game.server.GameRoll.getEntities;

public class Entities {
    static Logger logger = LoggerFactory.getLogger(Entities.class);
    public static Entity cow;
    public static Entity man;
    public static Entity nullEntity;
    public static Entity kfcEntity;
    public static Entity mouse;
    public static Entity randomEntity;
    public static Entity divisionZeroEntity;
    public static Entity chinese;
    public static void initEntity()  {
        cow = getEntities().get(0);
        man = getEntities().get(1);
        nullEntity = getEntities().get(2);
        kfcEntity = getEntities().get(3);
        mouse = getEntities().get(4);
        divisionZeroEntity = getEntities().get(5);
        chinese = getEntities().get(6);
        randomEntity = getEntities().get(7);
        for(Entity entity:GameRoll.getEntities()){
            entity.setCritValue(10);
            entity.tick();
        }

        logger.debug("实体列表目前为{},大小为{}", getEntities(), getEntities().size());
    }

    public void summon(){
        try {
            try {
                Entity.summon(Entity.EntityType.COW, 150, "cow");
                Entity.summon(Entity.EntityType.MAN, 150, "man");
                Entity.summon(Entity.EntityType.NULL, 150, "bruce");
                Entity.summon(Entity.EntityType.KFC, 200, "kfc entity");
                Entity.summon(Entity.EntityType.MOUSE, 5, "mouse");
                Entity.summon(Entity.EntityType.DIVISION_ZERO,20,"jack");
                Entity.summon(Entity.EntityType.CHINESE,100,"chinese");
                try {
                    Entity.summon(Entity.EntityType.RANDOM_ENTITY, RandomUtil.getRandDouble(0, 1000), "[随机实体]" + RandomPool.RandomEntity.getRandType().toString());
                } catch (Exception e) {
                    logger.error("生成随机实体时出现如下异常：", e);
                }
            } catch (Exception e) {
                logger.error("生成实体时出现异常", e);
            }

            initEntity();
        } catch (Exception e) {
            logger.error("生成实体及初始化时出现异常", e);
        }
    }
}
