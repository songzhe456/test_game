package com.game.funny;

import com.game.Main;
import com.game.entity.Entity;
import com.game.entity.NullEntity;
import com.game.server.GameRoll;
import com.game.server.Server;

public class Flaw extends Main {
    public void flaw() {
        for(int i = 0;i < 100; i ++){
            Entity nullEntity = NullEntity.summon(Entity.EntityType.NULL,20,"flaw entity");
            GameRoll.addEntity(GameRoll.getEntities().size(),nullEntity);
        }
    }
}
