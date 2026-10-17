package com.game.entity.poster;

import com.game.entity.Entity;
import com.game.server.GameRoll;
import com.game.server.Server;

public class PacketPoster extends Entity implements Postable {

    public PacketPoster(double health, String id) {
        super(8, EntityType.POSTER, health, id);
    }

    @Override
    public void post(String content) {
        GameRoll.getInstance().getContext().writeAndFlush(content);
    }

    @Override
    public void attack(Entity target, double critValue) {
        super.attack(target, critValue);
        post("[poster]我攻击啦！");
    }
}
