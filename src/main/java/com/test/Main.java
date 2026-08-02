package com.test;

import com.test.client.Client;
import com.test.user.User;
import com.test.util.RandomUtil;
import com.test.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static RandomUtil randomCreator = new RandomUtil();
    static void main(String[] args) {
        Runnable runnable = () -> {
            logger.info("程序开始运行");
            User user = new User();
            user.scanMessage();
            Runnable serverRunnable = () -> {

                new Server().run();
            };
            Thread serverThread = new Thread(serverRunnable);
            serverThread.setName("Server Thread");
            serverThread.start();
            Runnable clientRunnable = () -> {
                new Client().connect();

            };
            Thread clientThread = new Thread(clientRunnable);
            clientThread.setName("Client Thread");
            clientThread.start();
        };

            Thread mainThread = new Thread(runnable);
            mainThread.setName("Main Thread");
            mainThread.start();
    }
}
