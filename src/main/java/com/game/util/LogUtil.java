package com.game.util;

import com.game.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class LogUtil {
    private static final Logger SERVER_LOGGER = LoggerFactory.getLogger(Server.class);
    public static void printInfo(String msg,Object... arg){
        SERVER_LOGGER.info(msg,arg);
    }

    public static void printError(String msg,Object... arg){
        SERVER_LOGGER.error(msg,arg);
    }

    public static void printWarn(String msg,Object... arg){
        SERVER_LOGGER.warn(msg,arg);
    }

    public static void printDebug(String msg,Object... arg){
        SERVER_LOGGER.debug(msg,arg);
    }
}