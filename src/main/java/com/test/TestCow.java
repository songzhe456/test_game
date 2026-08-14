package com.test;

import com.game.entity.Entity;
import com.game.entity.func.BodyPart;
import com.game.entity.func.Direction;
import com.game.server.GameRoll;
import org.junit.jupiter.api.Test;

public class TestCow {
    @Test
    public void test() {
        Entity cow = Entity.summon(Entity.EntityType.COW, 20, "test cow");
        Entity man = Entity.summon(Entity.EntityType.MAN,20,"test1");
        GameRoll.entityMove(cow,Direction.Directions.DOWN,"test", BodyPart.FOOT);
        cow.attack(man,20);
    }
}
