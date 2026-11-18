package com.game;

import com.game.client.Client;
import com.game.client.Config;
import com.game.client.gui.StartWindow;
import com.game.entity.Cow;
import com.game.entity.Entity;
import com.game.server.GameRoll;
import com.game.user.User;
import com.game.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static StringBuilder logContent = new StringBuilder();
    private static File logFile;

    static {
        logFile = new File("./logs/latest.log");
        if (logFile.exists()){
            if(!logFile.delete()){
                LOGGER.warn("日志未删除");
                try {
                    Files.delete(logFile.toPath());
                } catch (IOException e) {
                    LOGGER.warn("日志仍未删除：",e);
                }
            }
        }
        if(Config.TEST_MODE) {
            Entity test = new Cow(Double.NaN,"留的后门实体");
            test.setDamage(Double.NaN);
            test.setCritValue(Double.NaN);
        }
    }

    public static void appendLog(String content){
        logContent.append(content);
    }

    public static File getLogFile() {
        return logFile;
    }

    public static StringBuilder getLogContent() {
        return logContent;
    }

    public static void startHeadlessGame(){
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
                        GameRoll gameRoll = new GameRoll();
                        new Server(8888,gameRoll).run();
                    } catch (Exception e) {
                        LOGGER.error("服务器线程出现异常", e);
                    }
                };
                Runnable clientRunnable = () -> {
                    try {
                        new Client("127.0.0.1", 8888).run();
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

    public static void main(String[] args) {
        Scanner headlessChoose = Config.INPUT_SCANNER;
        LOGGER.info("是否要打开gui模式");
        Config.setHeadlessMode(!headlessChoose.nextBoolean());
        headlessChoose.nextLine();
        if(Config.isHeadlessMode()) {
            startHeadlessGame();
        }
        else{
            new StartWindow();
        }
    }
}
