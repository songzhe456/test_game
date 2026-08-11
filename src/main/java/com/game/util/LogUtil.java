package com.game.util;

import com.game.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class LogUtil {
    private static final Logger serverLogger = LoggerFactory.getLogger(Server.class);
    public static void printInfo(String msg,Object... arg){
        serverLogger.info(msg,arg);
    }

    public static void printError(String msg,Object... arg){
        serverLogger.error(msg,arg);
    }

    public static void printWarn(String msg,Object... arg){
        serverLogger.warn(msg,arg);
    }

    public static void printDebug(String msg,Object... arg){
        serverLogger.debug(msg,arg);
    }
}