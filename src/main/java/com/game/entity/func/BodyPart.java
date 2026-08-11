package com.game.entity.func;

import com.game.entity.Entities;
import com.game.entity.Entity;

public enum BodyPart {
    HAND(Entities.cow,0),
    HEAD(Entities.cow,0);

    private final Entity entity;
    private final int index;
    BodyPart(Entity entity, int index){
        this.entity = entity;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
