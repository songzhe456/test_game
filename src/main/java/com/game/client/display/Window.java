package com.game.client.display;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Window extends Application {
    Logger logger = LoggerFactory.getLogger(Window.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label label = new Label("欢迎进入");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root,400,300);
        primaryStage.setTitle("实体大乱斗");
        primaryStage.setScene(scene);
        primaryStage.show();
        logger.info("窗口已显示");
        while(true) {
            if (!primaryStage.isShowing()) {
                label = null;
                root = null;
                scene = null;
                primaryStage.close();
                if (scene == null) {
                    logger.info("窗口已关闭");
                    break;
                }
            }
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
