package com.game.func;

import com.game.entity.Entity;
import com.game.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);
    public static void nullPointerExceptionTrigger(Entity.EntityType type) {
        if (type.equals(Entity.EntityType.NULL)) {
            try {
                throw new NullPointerException("实体类型为" + Entity.EntityType.NULL);
            } catch (Exception e) {
                logger.error("[是故意的]实体类型为NULL导致了以下异常:", e);
            }
        }
    }
    public static void nullPointerExceptionTrigger(Item.ItemType type)  {
        if (type.equals(Item.ItemType.NULL)) {
            try {
                throw new NullPointerException("物品类型为" + Item.ItemType.NULL);
            } catch (Exception e) {
                logger.error("[不是真的！]物品类型为NULL导致了以下异常:", e);
            }
        }
    }
}