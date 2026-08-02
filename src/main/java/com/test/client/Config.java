package com.test.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
    public static String FIGHT_WORD;
    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    public void configCheck(){
        logger.info("以下配置已生效: [FIGHT_WORD={}]",FIGHT_WORD);
    }
}
