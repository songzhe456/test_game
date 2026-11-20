package com.game.func;

import com.game.archive.Archive;
import com.game.client.Config;
import com.game.entity.Entity;
import com.game.entity.func.Direction;
import com.game.server.GameRoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.game.entity.func.BodyPart.FOOT;

public class Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(Command.class);
    //']'为命令终止字符
    public static final char COMMAND_END = ']';
    public static final char SPACE = ' ';

    public void scanMove(Entity entity, String id) {
        if(!Config.isHeadlessMode()){return;}
        Scanner scanMove = Config.INPUT_SCANNER;
        System.out.println("请输入方向");
        String direction = scanMove.nextLine();
        try {
            switch (direction) {
                case ("forward"):
                    GameRoll.entityMove(entity, Direction.Directions.FORWARD, id, FOOT);
                    break;
                case ("backward"):
                    GameRoll.entityMove(entity, Direction.Directions.BACKWARD, id, FOOT);
                    break;
                case ("left"):
                    GameRoll.entityMove(entity, Direction.Directions.LEFT, id, FOOT);
                    break;
                case ("right"):
                    GameRoll.entityMove(entity, Direction.Directions.RIGHT, id, FOOT);
                    break;
                case ("up"):
                    GameRoll.entityMove(entity, Direction.Directions.UP, id, FOOT);
                    break;
                case ("down"):
                    GameRoll.entityMove(entity, Direction.Directions.DOWN, id, FOOT);
                    break;
                case null, default:
                    GameRoll.entityMove(entity, null, id, null);
                    break;
            }
        } catch (Exception e) {
            LOGGER.warn("位置不合法", e);
        }
    }

    public Entity chooseEntity() {
        if(!Config.isHeadlessMode()){return null;}
        Scanner chooser = Config.INPUT_SCANNER;
        String entity = chooser.nextLine();
        if ("exit".equals(entity)) {
            System.out.println("进程已退出");
            System.exit(0);
        }
        Entity chosen = null;
        try {
            for (Entity e : GameRoll.getEntities()) {
                if (entity.equals(e.getId())) {
                    chosen = e;
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.warn("该实体不存在");
            return null;
        }
        return chosen;
    }

    public boolean autoFightCommand(){
        if(!Config.isHeadlessMode()){return false;}
        Scanner scanner = Config.INPUT_SCANNER;
        String answer = scanner.nextLine();
        return "是".equals(answer);
    }

    public void showEntityArchive(Archive archive,String name){
        if(GameRoll.getEntities().isEmpty()){
            LOGGER.warn("暂无实体，请先生成实体！");
        }
        for (Entity entity : GameRoll.getEntities()){
            if (name.equals(entity.getId())){
                archive.showEntityArchive(entity.getId(),entity.getHealth());
            }
        }
    }

    public void handlePreCommand(String command){
        StringBuilder sb = new StringBuilder();
        ArrayList<String> commands = new ArrayList<>();
        for (int charPoint : command.chars().toArray()){
            for (char ch : Character.toChars(charPoint)){
                if(ch == SPACE){
                    commands.add(sb.toString());
                    sb = new StringBuilder();
                }
                else if (ch == COMMAND_END) {
                    commands.add(sb.toString());
                }
                else {
                    sb.append(ch);
                }
            }
        }

        String baseCommand = "";
        CopyOnWriteArrayList<String> args = new CopyOnWriteArrayList<>();
        for (String cmd : commands) {
            if(cmd.equals(commands.getFirst())){
                baseCommand = cmd;
            }
            else{
                args.add(cmd);
            }
        }

        switch (baseCommand){
            case "exit" -> System.exit(0);
            case "show_archive" -> showEntityArchive(GameRoll.getInstance().getArchive(), args.getFirst());
            default -> LOGGER.warn("未知命令：{} {}",baseCommand,args);
        }
    }
}
