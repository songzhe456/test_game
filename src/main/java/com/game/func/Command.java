package com.game.func;

import com.game.entity.Entity;
import com.game.entity.func.Direction;
import com.game.server.GameRoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Scanner;

public class Command {
    Logger logger = LoggerFactory.getLogger(Command.class);

    public void scanMove(Entity entity, String id) {
        Scanner scanMove = new Scanner(System.in);
        System.out.println("请输入方向");
        String direction = scanMove.nextLine();
        try {
            switch (direction) {
                case ("forward"):
                    GameRoll.entityMove(entity, Direction.Directions.FORWARD, id);
                    break;
                case ("backward"):
                    GameRoll.entityMove(entity, Direction.Directions.BACKWARD, id);
                    break;
                case ("left"):
                    GameRoll.entityMove(entity, Direction.Directions.LEFT, id);
                    break;
                case ("right"):
                    GameRoll.entityMove(entity, Direction.Directions.RIGHT, id);
                    break;
                case ("up"):
                    GameRoll.entityMove(entity, Direction.Directions.UP, id);
                    break;
                case ("down"):
                    GameRoll.entityMove(entity, Direction.Directions.DOWN, id);
                    break;
                case null, default:
                    GameRoll.entityMove(entity, null, id);
                    break;
            }
        } catch (Exception e) {
            logger.warn("位置不合法");
        }
    }

    public Entity chooseEntity() {
        Scanner chooser = new Scanner(System.in);
        String entity = chooser.nextLine();
        Entity chosen = null;
        try {
            for (int i = 0; i < GameRoll.getEntities().size(); i++) {
                if (entity.equals(GameRoll.getEntities().get(i).getId())) {
                    chosen = GameRoll.getEntities().get(i);
                }
            }
        } catch (Exception e) {
            logger.warn("该实体不存在");
            return null;
        }
        return chosen;
    }
}
