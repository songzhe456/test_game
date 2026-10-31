package com.game.entity.baby;

import com.game.entity.Eater;
import com.game.entity.Entity;
import com.game.entity.baby.BabyGrower.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Baby extends Eater {
    private BabyGrower grower;
    private static final Logger LOGGER = LoggerFactory.getLogger(Baby.class);
    private State state = State.BABY;
    public Baby(double health, String id,BabyGrower grower) {
        super(10, EntityType.BABY, health, id);
        this.grower = grower;
        this.grower.babies.add(this);
    }

    public void setState(State state) {
        this.state = state;
    }

    public BabyGrower getGrower() {
        return grower;
    }

    public State getState() {
        return state;
    }

    @Override
    public void attack(Entity target, double critValue) {
        if(State.BABY.equals(state)){
            LOGGER.warn("{}太小，还无法进行攻击",this);
        }
        else{
            super.attack(target, critValue);
        }
    }
}
