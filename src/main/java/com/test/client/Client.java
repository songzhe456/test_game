package com.test.client;

import com.test.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private static Server server = new Server();
    private static Socket client = new Socket();
    private static final int PORT = 8888;
    private static Server.ClientHandler clientHandler = new Server.ClientHandler();
    private static long startTime;
    private static long endTime;
    private static boolean serverNeedWait;

    public static void connect() {
        try {
            Runnable clientHandleRunnable = () -> {
                while (!client.isConnected()) {
                    try {
                        client.connect(new InetSocketAddress("127.0.0.1", PORT), 5000);
                        if (client.isConnected()) {
                            logger.info("客户端已连接");
                            logger.info("即将发送消息");
                            Runnable postRunnable = () -> {
                                postMessage(Config.FIGHT_WORD);
                            };
                            Thread postThread = new Thread(postRunnable);
                            postThread.setName("Post Thread");
                            postThread.start();

                        } else {
                            throw new IOException("客户端连接失败");
                        }
                    } catch (Exception e) {
                        logger.error("客户端连接失败", e);
                        return;
                    }
                    logger.info("等待服务器响应");
                    clientHandler.run();
                }
            };
            Thread clientHandleThread = new Thread(clientHandleRunnable);
            clientHandleThread.setName("Client Handle Thread");
            clientHandleThread.start();
        } catch (Exception e) {
            logger.error("客户端连接出现异常", e);
        }
    }

    public static Socket getClient() {
        return client;
    }

    public static Server.ClientHandler getClientHandler() {
        return clientHandler;
    }

    public static void postMessage(String msg) {
        clientHandler.clientPostMessage(msg);
        logger.info("已发送{}",msg);
    }

    public static long getStartTime() {
        return startTime;
    }

    public static long getEndTime() {
        return endTime;
    }

    public static boolean isServerNeedWait() {
        return serverNeedWait;
    }
}
