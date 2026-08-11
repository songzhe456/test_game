package com.game.funny;

import com.game.Main;
import com.game.entity.NullEntity;
import com.game.server.GameRoll;
import com.game.server.Server;

public class Flaw extends Main {
    public void flaw() throws Exception {
        while (true){
            GameRoll.addEntity(-1,new NullEntity(20,null));
        }
    }
}
