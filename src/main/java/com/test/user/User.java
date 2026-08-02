package com.test.user;

import com.test.client.Client;
import com.test.client.Config;

import java.util.Scanner;

public class User {
    public void scanMessage(){
        Scanner messageScanner = new Scanner(System.in);
        System.out.println("请输入战前贺词：");
        Config.FIGHT_WORD = messageScanner.nextLine();
    }
}
