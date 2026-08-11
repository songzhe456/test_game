package com.game.client;

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
                logger.info("客户端程序开始运行");
                User user = new User();
                if (Config.NEED_INPUT) {
                    user.scanMessage();
                }
                Runnable clientRunnable = () -> {
                    try {
                        new Client("127.0.0.1",8888).run();
                    } catch (Exception e) {
                        logger.error("客户端线程出现异常", e);
                    }
                };
                Thread clientThread = new Thread(clientRunnable);
                clientThread.setName("Client Thread");
                clientThread.start();
            } catch (Exception e) {
                logger.error("客户端程序出现异常", e);
            }
        };

        Thread mainThread = new Thread(runnable);
        mainThread.setName("Main Thread");
        mainThread.start();
    }
}
