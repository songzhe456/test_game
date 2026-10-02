package com.game;

import com.game.client.Client;
import com.game.client.Config;
import com.game.user.User;
import com.game.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Runnable runnable = () -> {
            try {
                LOGGER.info("程序开始运行");
                User user = new User();
                Scanner clientOrServerChoose = Config.INPUT_SCANNER;

                //当配置NEED_INPUT为true时用户需要输入祝战贺词(除此处和选择启动端，其他输入暂时不受影响)
                if (Config.NEED_INPUT) {
                    user.scanMessage();
                }
                Runnable serverRunnable = () -> {
                    try {
                        new Server(8888).run();
                    } catch (Exception e) {
                        LOGGER.error("服务器线程出现异常", e);
                    }
                };
                Runnable clientRunnable = () -> {
                    try {
                        new Client("127.0.0.1",8888).run();
                    } catch (Exception e) {
                        LOGGER.error("客户端线程出现异常", e);
                    }
                };
                if (Config.NEED_INPUT) {
                    System.out.println("输入“client”启动客户端,输入“server”启动服务端,输入all将同时启动两个");
                    String clientServerInput = clientOrServerChoose.nextLine();
                    switch (clientServerInput) {
                        case "client" -> {
                            Thread clientThread = new Thread(clientRunnable);
                            clientThread.setName("Client Thread");
                            clientThread.start();
                        }
                        case "server" -> {
                            Thread serverThread = new Thread(serverRunnable);
                            serverThread.setName("Server Thread");
                            serverThread.start();
                        }
                        case "all" -> {
                            Thread serverThread = new Thread(serverRunnable);
                            serverThread.setName("Server Thread");
                            serverThread.start();
                            Thread clientThread = new Thread(clientRunnable);
                            clientThread.setName("Client Thread");
                            clientThread.start();
                        }
                    }
                } else {
                    LOGGER.info("已自动选择all");
                    Thread serverThread = new Thread(serverRunnable);
                    serverThread.setName("Server Thread");
                    serverThread.start();
                    Thread clientThread = new Thread(clientRunnable);
                    clientThread.setName("Client Thread");
                    clientThread.start();
                }
            } catch (Exception e) {
                LOGGER.error("程序出现异常", e);
            }
        };

        Thread mainThread = new Thread(runnable);
        mainThread.setName("Main Thread");
        mainThread.start();
    }
}
