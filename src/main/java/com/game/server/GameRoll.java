package com.game.server;

import com.game.client.Config;
import com.game.entity.Entities;
import com.game.entity.Entity;
import com.game.entity.func.BodyPart;
import com.game.entity.func.Direction;
import com.game.func.Command;
import com.game.func.Fight;
import com.game.item.Foods;
import com.game.item.Item;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static com.game.entity.Entities.*;

public class GameRoll implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRoll.class);
    private static int ROLL_COUNT = 1;
    private static int reTries = 0;
    private static final ArrayList<Entity> ENTITY_LIST = new ArrayList<>();
    private static final ArrayList<Item> ITEM_LIST = new ArrayList<>();
    private static final Fight FIGHT = new Fight();
    private static final Command COMMAND = new Command();

    public static void addEntity(int index, Entity entity) {
        try {
            ENTITY_LIST.add(index, entity);
        } catch (Exception e) {
            LOGGER.error("实体列表扩充失败：",e);
            ENTITY_LIST.add(index,null);
        }
    }

    public static void addItem(Item item) {
        ITEM_LIST.add(item);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            try {
                new Config().configCheck();
                verify();
            } catch (Exception e) {
                LOGGER.warn("验证出现异常,服务端即将关闭", e);
                System.exit(1);
            }
        } catch (Exception e) {
            LOGGER.error("运行时出现异常", e);
        }
    }

    public void fight() {
        Thread createItemThread = getCreateItemThread();
        createItemThread.start();
        try {
            Runnable createEntityRunnable = () -> {
                new Entities().summon();
                try {
                    Runnable rollRunnable = () -> {
                        LOGGER.info("回合线程内实体列表目前为{},大小为{}", ENTITY_LIST, ENTITY_LIST.size());
                        while (true) {
                            try {
                                LOGGER.info("---第{}轮---", ROLL_COUNT);
                                if (man != null && man.getHealth() <= 0) {
                                    man.die();
                                    man = null;
                                } else if (cow != null && cow.getHealth() <= 0) {
                                    cow.die();
                                    cow = null;
                                } else if (nullEntity != null && nullEntity.getHealth() <= 0) {
                                    nullEntity.die();
                                    nullEntity = null;
                                } else if (kfcEntity != null && kfcEntity.getHealth() <= 0) {
                                    kfcEntity.die();
                                    kfcEntity = null;
                                } else if (mouse != null && mouse.getHealth() <= 0) {
                                    mouse.die();
                                    mouse = null;
                                } else if (divisionZeroEntity != null && divisionZeroEntity.getHealth() <= 0) {
                                    divisionZeroEntity.die();
                                    divisionZeroEntity = null;
                                } else if (chinese != null && chinese.getHealth() <= 0) {
                                    chinese.die();
                                    chinese = null;
                                } else if (randomEntity != null && randomEntity.getHealth() <= 0) {
                                    randomEntity.die();
                                    randomEntity = null;
                                } else {
                                    LOGGER.warn("所有实体都已在场");
                                }
                                System.out.println("请选择要移动的实体");
                                try {
                                    Entity chosen = COMMAND.chooseEntity();
                                    COMMAND.scanMove(chosen, chosen.getId());
                                } catch (Exception e) {
                                    LOGGER.warn("实体不存在");
                                }
                                System.out.println("请输入攻击者(输入exit退出)");
                                Entity attacker = COMMAND.chooseEntity();
                                Entity attackerEntity = null;
                                if (attacker != null)
                                    switch (attacker.getType()) {
                                        case COW:
                                            attackerEntity = cow;
                                            break;
                                        case KFC:
                                            attackerEntity = kfcEntity;
                                            break;
                                        case MAN:
                                            attackerEntity = man;
                                            break;
                                        case MOUSE:
                                            attackerEntity = mouse;
                                            break;
                                        case NULL:
                                            attackerEntity = nullEntity;
                                            break;
                                        case RANDOM_ENTITY:
                                            attackerEntity = randomEntity;
                                            break;
                                        case DIVISION_ZERO:
                                            attackerEntity = divisionZeroEntity;
                                            break;
                                        case CHINESE:
                                            attackerEntity = chinese;
                                            break;
                                        case POSTER:
                                            attackerEntity = poster;
                                            break;
                                        default:
                                            LOGGER.warn("{}不存在", attacker);
                                    }
                                else {
                                    LOGGER.warn("攻击者不存在");
                                }
                                System.out.println("请输入攻击目标(输入exit退出)");
                                Entity target = COMMAND.chooseEntity();
                                Entity targetEntity = null;
                                if(target != null) {
                                    switch (target.getType()) {
                                        case COW:
                                            targetEntity = cow;
                                            break;
                                        case KFC:
                                            targetEntity = kfcEntity;
                                            break;
                                        case MAN:
                                            targetEntity = man;
                                            break;
                                        case MOUSE:
                                            targetEntity = mouse;
                                            break;
                                        case NULL:
                                            targetEntity = nullEntity;
                                            break;
                                        case RANDOM_ENTITY:
                                            targetEntity = randomEntity;
                                            break;
                                        case DIVISION_ZERO:
                                            targetEntity = divisionZeroEntity;
                                            break;
                                        case CHINESE:
                                            targetEntity = chinese;
                                            break;
                                        case POSTER:
                                            targetEntity = poster;
                                            break;
                                        default:
                                            LOGGER.warn("{}不存在", target);
                                    }
                                }
                                else {
                                    LOGGER.warn("目标不存在");
                                }
                                FIGHT.fight(attackerEntity, targetEntity);
                                LOGGER.info("目前实体列表为{}", GameRoll.getEntities());
                                LOGGER.info("目前物品列表为{}", GameRoll.getItems());
                                if (ROLL_COUNT >= 999) {
                                    try {
                                        throw new RuntimeException("达到回合上限");
                                    } catch (Exception e) {
                                        LOGGER.error(e.getMessage(), e);
                                        System.exit(-1);
                                    }
                                }
                                ROLL_COUNT += 1;
                            } catch (Exception e) {
                                LOGGER.error("循环出现异常", e);
                                return;
                            }
                        }
                    };
                    Thread rollThread = new Thread(rollRunnable);
                    rollThread.setName("Roll Thread");
                    rollThread.start();
                } catch (Exception e) {
                    LOGGER.error("回合线程发生如下异常：", e);
                }
            };
            Thread createEntityThread = new Thread(createEntityRunnable);
            createEntityThread.setName("Entity Summon Thread");
            createEntityThread.start();
        } catch (Exception e) {
            LOGGER.error("实体生成线程出现异常", e);
        }
    }

    private @NotNull Thread getCreateItemThread() {
        Runnable createItemRunnable = () -> {
            try {
                Foods.create(Foods.FoodType.APPLE, "apple");
                Foods.create(Foods.FoodType.GREAT_APPLE, "great apple");
                Foods.create(Foods.FoodType.COKE, "coke");
                Item.create("null", null);
            } catch (Exception e) {
                LOGGER.error("物品创建出现异常", e);
            } finally {
                LOGGER.info("当前物品列表为{}", ITEM_LIST);
            }
        };
        return new Thread(createItemRunnable, "Item Create Thread");
    }

    public void verify() {
        if (Config.NEED_VERIFY && Server.ServerHandler.getContent() != null && (Server.ServerHandler.getContent().contains("战斗") && (Server.ServerHandler.getContent().contains("强") || Server.ServerHandler.getContent().contains("good") || Server.ServerHandler.getContent().contains("好") || Server.ServerHandler.getContent().contains("爽")))) {
            LOGGER.info("成功接收到客户端传入的“{}”战前祝词，即将开始战斗", Server.ServerHandler.getContent());
            fight();
        } else if (Config.NEED_VERIFY && Server.ServerHandler.getContent() == null && reTries < 3) {
            while (true) {
                LOGGER.warn("消息获取失败，即将重试");
                reTries += 1;
                if (reTries >= 3) {
                    LOGGER.warn("重试次数已达上限（{}次）", reTries);
                    break;
                }
            }
        } else if (Config.NEED_VERIFY && Server.ServerHandler.getContent() != null && (!Server.ServerHandler.getContent().contains("战斗") || (!Server.ServerHandler.getContent().contains("强") || !Server.ServerHandler.getContent().contains("good") || !Server.ServerHandler.getContent().contains("好") || !Server.ServerHandler.getContent().contains("爽")))) {
            throw new NegativeClientException("客户端的战斗祝词不够有劲");
        } else if (!Config.NEED_VERIFY) {
            LOGGER.info("可直接开战");
            fight();
        } else {
            LOGGER.warn("程序未触发分支");
        }
    }

    public static ArrayList<Entity> getEntities() {
        return ENTITY_LIST;
    }

    public static ArrayList<Item> getItems() {
        return ITEM_LIST;
    }

    public static void entityMove(Entity entity, Direction.Directions directions, String id, BodyPart part) {
        entity.move(directions, id);
        LOGGER.info("{}的{}往{}移动了", id, part,directions);
    }
}
