package com.test.server;

import com.test.client.Client;
import com.test.client.Config;
import com.test.entity.Entity;
import com.test.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private static final int PORT = 8888;
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private static ServerSocket server = null;

    static {
        try {
            server = new ServerSocket(PORT);
        } catch (Exception e) {
            LOGGER.error("服务器启动失败");
        }
    }

    public void run() {
        try {
            LOGGER.info("服务器已启动");
            if (server != null) {
                if (Client.getClient() != null) {
                    LOGGER.info("等待客户端连接...");
                } else {
                    throw new IOException("客户端不存在");
                }
            } else if (server == null) {
                throw new IOException("服务器未启动");
            }
        } catch (Exception e) {
            LOGGER.error("服务器出现异常：", e);
        }
    }

    public static class ClientHandler implements Runnable {
        private String msg;
        private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
        private static int ROLL_COUNT = 1;
        private static int reTries = 0;
        private static boolean canNext;
        private static ArrayList<Entity> entityList = new ArrayList<>();
        private static Entity cow;
        private static Entity man;
        private static Entity nullEntity;
        private static Entity kfcEntity;
        private static Entity mouse;

        public static void initEntity() {
            cow = getEntities().get(0);
            man = getEntities().get(1);
            nullEntity = getEntities().get(2);
            kfcEntity = getEntities().get(3);
            mouse = getEntities().get(4);
        }

        public static void addEntity(Entity entity) {
            entityList.add(entity);
        }

        public static void setCanNext(boolean canNext) {
            ClientHandler.canNext = canNext;
        }

        @Override
        public void run() {
            try {
                try {
                    Thread.sleep(1);
                    new Config().configCheck();
                    verify();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                logger.error("运行时出现异常", e);
            }
        }

        public void clientPostMessage(String msg) {
            setMsg(msg);
        }

        public void fight() {
            Runnable createEntityRunnable = () -> {
                try {
                    addEntity(Entity.summon("cow", 150, "cow"));
                    addEntity(Entity.summon("man", 150, "man"));
                    addEntity(Entity.summon("null", 150, "bruce"));
                    addEntity(Entity.summon("kfc entity", 200, "kfc entity"));
                    addEntity(Entity.summon("mouse", 5, "mouse"));
                    initEntity();

                } catch (Exception e) {
                    logger.error("生成实体时出现异常", e);
                }
                Runnable createRandomEntityRunnable = () -> {
                    try {
                        RandomUtil.getRandEntity();
                    } catch (Exception e) {
                        logger.error("生成随机实体时出现如下异常：", e);
                    }
                };
                Thread createRandomEntityThread = new Thread(createRandomEntityRunnable);
                createRandomEntityThread.setName("Random Entity Summon Thread");
                createRandomEntityThread.start();
                try {
                    Runnable rollRunnable = () -> {
                        while (true) {
                            try {
                                logger.info("---第{}轮---", ROLL_COUNT);
                                rollAttack();

                                if (man.getHealth() <= 0) {
                                    man.die();
                                    return;
                                }
                                if (cow.getHealth() <= 0) {
                                    cow.setHealth(0);
                                    cow.die();
                                    return;
                                }
                                if (nullEntity.getHealth() <= 0) {
                                    nullEntity.setHealth(0);
                                    nullEntity.die();
                                    return;
                                }
                                if (kfcEntity.getHealth() <= 0) {
                                    kfcEntity.setHealth(0);
                                    kfcEntity.die();
                                    return;
                                }
                                if (mouse.getHealth() <= 0) {
                                    mouse.setHealth(0);
                                    mouse.die();
                                    return;
                                }
                                logger.debug("目前实体列表为{}", Server.ClientHandler.getEntities());

                                if (ROLL_COUNT >= 999) {
                                    try {
                                        throw new RuntimeException("达到回合上限");
                                    } catch (Exception e) {
                                        logger.error(e.getMessage(), e);
                                        System.exit(-1);
                                    }
                                }
                                ROLL_COUNT += 1;
                            } catch (Exception e) {
                                logger.error("循环出现异常", e);
                                return;
                            }
                        }
                    };

                    Thread rollThread = new Thread(rollRunnable);
                    rollThread.setName("Roll Thread");
                    rollThread.start();
                } catch (Exception e) {
                    logger.error("回合线程发生如下异常：", e);
                }
            };
            Thread createEntityThread = new Thread(createEntityRunnable);
            createEntityThread.setName("Entity Summon Thread");
            createEntityThread.start();
        }

        public void rollAttack() {
            man.setCritValue(10);

            man.attack(nullEntity, nullEntity.getHealth());

            logger.info("{}造成了暴击{}", man.getId(), man.getCritValue());
            cow.setCritValue(5);

            cow.attack(man, man.getHealth());
            logger.info("{}造成了暴击{}", cow.getId(), cow.getCritValue());

            nullEntity.setCritValue(5);

            nullEntity.attack(cow, cow.getHealth());
            logger.info("{}造成了暴击{}", nullEntity.getId(), nullEntity.getCritValue());

            kfcEntity.setCritValue(5);

            kfcEntity.attack(nullEntity, nullEntity.getHealth());
            logger.info("{}造成了暴击{}", kfcEntity.getId(), kfcEntity.getCritValue());

            mouse.setCritValue(5);

            mouse.attack(kfcEntity, kfcEntity.getHealth());
            logger.info("{}造成了暴击{}", mouse.getId(), mouse.getCritValue());
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void verify() {
            if (msg != null && (msg.contains("战斗") && (msg.contains("强") || msg.contains("good") || msg.contains("好") || msg.contains("爽")))) {
                LOGGER.info("成功接收到客户端传入的“{}”战前祝词，即将开始战斗", msg);
                fight();

            } else if (msg == null && reTries < 3) {
                while (true) {
                    LOGGER.info("消息获取失败，即将重试");
                    reTries += 1;
                    if (reTries >= 3) {
                        logger.warn("重试次数已达上限（{}次）", reTries);
                        break;
                    }
                }
            } else if (msg != null && (!msg.contains("战斗") || (!msg.contains("强") || !msg.contains("good") || !msg.contains("好") || !msg.contains("爽")))) {
                throw new NegativeClientException("客户端的战斗祝词不够有劲");
            } else {
                LOGGER.info("程序未触发分支");
            }
        }

        public static ArrayList<Entity> getEntities() {
            return entityList;
        }
    }
}