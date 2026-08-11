package com.game.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
    public static String FIGHT_WORD;
    public static final boolean NEED_VERIFY = false;
    public static final boolean NEED_INPUT = true;
    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    public void configCheck(){
        logger.info("以下配置已生效: [FIGHT_WORD={},NEED_VERIFY={},NEED_INPUT={}]",FIGHT_WORD,NEED_VERIFY,NEED_INPUT);
    }
}
