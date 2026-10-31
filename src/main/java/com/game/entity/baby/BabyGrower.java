package com.game.entity.baby;

import java.util.concurrent.CopyOnWriteArrayList;

public class BabyGrower {
    protected CopyOnWriteArrayList<Baby> babies = new CopyOnWriteArrayList<>();
    public enum State{
        BABY,
        YOUNG,
        TEENAGER,
        ADULT,
        OLD,
        UNKNOWN
    }

    public State nextState(State state){
        return switch (state){
            case BABY -> State.YOUNG;
            case YOUNG -> State.TEENAGER;
            case TEENAGER -> State.ADULT;
            case ADULT -> State.OLD;
            default -> State.UNKNOWN;
        };
    }
}
