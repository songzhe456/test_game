package com.game.func;

import com.game.client.Config;
import com.game.entity.Entity;
import com.game.entity.func.Direction;
import com.game.server.GameRoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import static com.game.entity.func.BodyPart.FOOT;

public class Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(Command.class);

    public void scanMove(Entity entity, String id) {
        Scanner scanMove = Config.INPUT_SCANNER;
        System.out.println("请输入方向");
        String direction = scanMove.nextLine();
        try {
            switch (direction) {
                case ("forward"):
                    GameRoll.entityMove(entity, Direction.Directions.FORWARD, id, FOOT);
                    break;
                case ("backward"):
                    GameRoll.entityMove(entity, Direction.Directions.BACKWARD, id,FOOT);
                    break;
                case ("left"):
                    GameRoll.entityMove(entity, Direction.Directions.LEFT, id,FOOT);
                    break;
                case ("right"):
                    GameRoll.entityMove(entity, Direction.Directions.RIGHT, id,FOOT);
                    break;
                case ("up"):
                    GameRoll.entityMove(entity, Direction.Directions.UP, id,FOOT);
                    break;
                case ("down"):
                    GameRoll.entityMove(entity, Direction.Directions.DOWN, id,FOOT);
                    break;
                case null, default:
                    GameRoll.entityMove(entity, null, id,null);
                    break;
            }
        } catch (Exception e) {
            LOGGER.warn("位置不合法");
        }
    }

    public Entity chooseEntity() {
        Scanner chooser = Config.INPUT_SCANNER;
        String entity = chooser.nextLine();
        Entity chosen = null;
        try {
            for (Entity e:GameRoll.getEntities()) {
                if (entity.equals(e.getId())) {
                    chosen = e;
                }
            }
        } catch (Exception e) {
            LOGGER.warn("该实体不存在");
            return null;
        }
        return chosen;
    }
}
