package com.game.achievement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Achievement {
    private String name;
    private boolean achieved;
    private static final Logger LOGGER = LoggerFactory.getLogger(Achievement.class);

    public Achievement(String name){
        this.name = name;
    }

    private void achieve(){
        if(!achieved) {
            achieved = true;
            LOGGER.info("你获得了成就“{}”", name);
        }
    }

    public void tryAchieve(boolean condition){
        if(condition){
            achieve();
        }
    }

    public void revoke(){
        achieved = false;
        LOGGER.info("你被剥夺了成就“{}”",name);
    }

    public String getName() {
        return name;
    }

    public boolean isAchieved() {
        return achieved;
    }
}
