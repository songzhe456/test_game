package com.game.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Config {
    public static String FIGHT_WORD;
    public static final Scanner INPUT_SCANNER = new Scanner(System.in);
    public static final boolean NEED_VERIFY = true;
    public static final boolean NEED_INPUT = true;
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    public void configCheck(){
        LOGGER.info("以下配置已生效: [FIGHT_WORD={},NEED_VERIFY={},NEED_INPUT={}]",FIGHT_WORD,NEED_VERIFY,NEED_INPUT);
    }
}
