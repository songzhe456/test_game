package com.test;

import com.game.entity.Entity;
import com.game.entity.func.BodyPart;
import com.game.entity.func.Direction;
import com.game.entity.func.PartFunc;
import org.junit.jupiter.api.Test;;

public class TestClass {
    @Test
    public void test()  {
        Entity cow = Entity.summon(Entity.EntityType.COW,20,"test");
        PartFunc partFunc = new PartFunc(BodyPart.HAND,cow, Direction.Directions.UP);
    }
}
