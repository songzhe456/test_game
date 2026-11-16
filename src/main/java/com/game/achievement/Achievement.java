package com.game.achievement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Achievement(String name,AchievementInstance instance) {
    private static final Logger LOGGER = LoggerFactory.getLogger(Achievement.class);

    private void achieve(){
        if(!instance().achieved) {
            instance().achieved = true;
            LOGGER.info("你获得了成就“{}”", name);
        }
    }

    public Achievement(String name){
        this(name,new AchievementInstance());
    }

    public void tryAchieve(boolean condition){
        if(condition){
            achieve();
        }
    }

    public void revoke(){
        instance().achieved = false;
        LOGGER.info("你被剥夺了成就“{}”",name);
    }

    public boolean isAchieved() {
        return instance().achieved;
    }

    protected static class AchievementInstance{
        private boolean achieved;
    }
}
