package com.game.entity.func;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Move {
    private static final Logger logger = LoggerFactory.getLogger(Move.class);
    private float x;
    private float y;
    private float z;

    public Move(Direction.Directions directions,String id){
        switch (directions){
            case DOWN:
                y -= 1;
                break;
            case UP:
                y += 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
            case FORWARD:
                z += 1;
                break;
            case BACKWARD:
                z -= 1;
                break;
            case NONE:
                break;
            default:
                logger.warn("{}未移动",id);
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @Override
    public String toString(){
        return "["+x+","+y+","+z+"]";
    }
}
