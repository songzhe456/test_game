package com.game.entity.poster;

import com.game.entity.Entity;
import com.game.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketPoster extends Entity implements Postable{

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketPoster.class);

    public PacketPoster(double health, String id) {
        super(8, EntityType.POSTER, health, id);
    }

    @Override
    public void post(String content) throws InterruptedException {
        Server.ServerHandler.getContext().writeAndFlush(content);
    }

    @Override
    public void attack(Entity target, double critValue) {
        try {
            super.attack(target, critValue);
            post("我攻击啦！");
        } catch (InterruptedException e) {
            LOGGER.error("{}攻击失败！原因：",this.getId(),e);
        }
    }
}
