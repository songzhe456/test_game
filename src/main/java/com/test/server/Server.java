package com.test.server;

import com.test.client.Client;
import com.test.client.Config;
import com.test.entity.Entity;
import com.test.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

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
                var entities = new Object() {
                    Entity cow = null;
                    Entity man = null;
                    Entity nullEntity = null;
                    Entity kfcEntity = null;
                };
                try {
                    entities.cow = Entity.summon("cow", 150, "cow");
                    entities.man = Entity.summon("man", 150, "man");
                    entities.nullEntity = Entity.summon("null", 150, "bruce");
                    entities.kfcEntity = Entity.summon("kfc entity",200,"kfc entity");
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
                                entities.man.setCritValue(10);
                                if (entities.man.getHealth() <= 0) {
                                    entities.man.die();
                                    return;
                                }
                                entities.man.attack(entities.nullEntity, entities.nullEntity.getHealth());

                                logger.info("{}造成了暴击{}", entities.man.getId(), entities.man.getCritValue());
                                entities.cow.setCritValue(5);
                                if (entities.cow.getHealth() <= 0) {
                                    entities.cow.die();
                                    return;
                                }
                                entities.cow.attack(entities.man, entities.man.getHealth());
                                logger.info("{}造成了暴击{}", entities.cow.getId(), entities.cow.getCritValue());

                                entities.nullEntity.setCritValue(5);
                                if (entities.nullEntity.getHealth() <= 0) {
                                    entities.nullEntity.die();
                                    return;
                                }
                                entities.nullEntity.attack(entities.cow, entities.cow.getHealth());
                                logger.info("{}造成了暴击{}", entities.nullEntity.getId(), entities.nullEntity.getCritValue());

                                entities.kfcEntity.setCritValue(5);
                                if (entities.kfcEntity.getHealth() <= 0) {
                                    entities.kfcEntity.die();
                                    return;
                                }
                                entities.kfcEntity.attack(entities.nullEntity, entities.nullEntity.getHealth());
                                logger.info("{}造成了暴击{}", entities.kfcEntity.getId(), entities.kfcEntity.getCritValue());

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

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void verify() {
            if (msg != null && (msg.contains("战斗") && (msg.contains("强") || msg.contains("good") || msg.contains("好") || msg.contains("爽")))) {
                LOGGER.info("成功接收到客户端传入的“{}”战前祝词，即将开始战斗", msg);
                fight();
            } else if (msg == null && reTries < 3) {
                LOGGER.info("消息获取失败，即将重试");
                reTries += 1;
                if (reTries >= 3) {
                    logger.warn("重试次数已达上限（{}次）", reTries);
                }
            } else if (msg != null && (!msg.contains("战斗") || (!msg.contains("强") || !msg.contains("good") || !msg.contains("好") || !msg.contains("爽")))) {
                throw new NegativeClientException("客户端的战斗祝词不够有劲");
            } else {
                LOGGER.info("程序未触发分支");
            }
        }
    }

    public static ServerSocket getServer() {
        return server;
    }

}
