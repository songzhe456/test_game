package com.game.server;

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

    static void main(String[] args) {
        Runnable runnable = () -> {
            try {
                logger.info("服务端程序开始运行");
                Runnable serverRunnable = () -> {
                    try {
                        new Server(8888).run();
                    } catch (Exception e) {
                        logger.error("服务器线程出现异常", e);
                    }
                };

                Thread serverThread = new Thread(serverRunnable);
                serverThread.setName("Server Thread");
                serverThread.start();
            } catch (Exception e) {
                logger.error("程序出现异常", e);
            }
        };
        Thread mainThread = new Thread(runnable);
        mainThread.setName("Main Thread");
        mainThread.start();
    }
}
