package com.test;

import com.game.entity.Cow;
import com.game.entity.Entity;
import com.game.entity.func.Direction;
import com.game.entity.func.Move;
import com.game.server.GameRoll;
import org.junit.jupiter.api.Test;;

public class TestClass {
    @Test
    public void test()  {
        GameRoll.entityMove(Entity.summon(Entity.EntityType.CHINESE,20.0,"chinese"),null,null);
    }
}
