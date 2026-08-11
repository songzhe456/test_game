package com.game;

import com.game.client.Client;
import com.game.client.Config;
import com.game.user.User;
import com.game.util.RandomUtil;
import com.game.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static RandomUtil randomCreator = new RandomUtil();

    static void main(String[] args) {
        Runnable runnable = () -> {
            try {
                logger.info("程序开始运行");
                User user = new User();
                Scanner clientOrServerChoose = new Scanner(System.in);

                //当配置NEED_INPUT为true时用户需要输入祝战贺词(除此处和选择启动端，其他输入暂时不受影响)
                if (Config.NEED_INPUT) {
                    user.scanMessage();
                }
                Runnable serverRunnable = () -> {
                    try {
                        new Server(8888).run();
                    } catch (Exception e) {
                        logger.error("服务器线程出现异常", e);
                    }
                };
                Runnable clientRunnable = () -> {
                    try {
                        new Client("127.0.0.1",8888).run();
                    } catch (Exception e) {
                        logger.error("客户端线程出现异常", e);
                    }
                };
                if (Config.NEED_INPUT) {
                    System.out.println("输入“client”启动客户端,输入“server”启动服务端,输入all将同时启动两个");
                    String clientServerInput = clientOrServerChoose.nextLine();
                    if (clientServerInput.equals("client")) {
                        Thread clientThread = new Thread(clientRunnable);
                        clientThread.setName("Client Thread");
                        clientThread.start();
                    } else if (clientServerInput.equals("server")) {
                        Thread serverThread = new Thread(serverRunnable);
                        serverThread.setName("Server Thread");
                        serverThread.start();
                    } else if (clientServerInput.equals("all")) {
                        Thread serverThread = new Thread(serverRunnable);
                        serverThread.setName("Server Thread");
                        serverThread.start();
                        Thread clientThread = new Thread(clientRunnable);
                        clientThread.setName("Client Thread");
                        clientThread.start();
                    }
                } else {
                    logger.info("已自动选择all");
                    Thread serverThread = new Thread(serverRunnable);
                    serverThread.setName("Server Thread");
                    serverThread.start();
                    Thread clientThread = new Thread(clientRunnable);
                    clientThread.setName("Client Thread");
                    clientThread.start();
                }
                clientOrServerChoose = null;
            } catch (Exception e) {
                logger.error("程序出现异常", e);
            }
        };

        Thread mainThread = new Thread(runnable);
        mainThread.setName("Main Thread");
        mainThread.start();
    }
}
