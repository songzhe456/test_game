package com.test.funny;

import com.test.Main;
import com.test.entity.NullEntity;
import com.test.server.Server;

public class Flaw extends Main {
    public void flaw(){
        while (true){
            Server.ClientHandler.addEntity(new NullEntity(20,null));
        }
    }
}
