package com.game.user;

import com.game.client.Config;

import java.util.Scanner;

public class User {
    //供玩家输入战前贺词
    public void scanMessage(){
        Scanner messageScanner = new Scanner(System.in);
        System.out.println("请输入战前贺词：");
        Config.FIGHT_WORD = messageScanner.nextLine();
        messageScanner = null;
    }
}
