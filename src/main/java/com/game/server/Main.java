package com.game.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    static void main(String[] args) {
        Runnable runnable = () -> {
            try {
                LOGGER.info("服务端程序开始运行");
                Runnable serverRunnable = () -> {
                    try {
                        new Server(8888).run();
                    } catch (Exception e) {
                        LOGGER.error("服务器线程出现异常", e);
                    }
                };

                Thread serverThread = new Thread(serverRunnable);
                serverThread.setName("Server Thread");
                serverThread.start();
            } catch (Exception e) {
                LOGGER.error("程序出现异常", e);
            }
        };
        Thread mainThread = new Thread(runnable);
        mainThread.setName("Main Thread");
        mainThread.start();
    }
}
