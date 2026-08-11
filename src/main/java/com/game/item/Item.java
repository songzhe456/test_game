package com.game.item;

import com.game.func.ExceptionUtils;
import com.game.server.GameRoll;
import com.game.server.Server;

import java.util.Objects;

public abstract class Item {
    String name;
    public Item(ItemType type,String name)  {
        this.name = name;
        ExceptionUtils.nullPointerExceptionTrigger(type);
        GameRoll.addItem(this);
    }
    public enum ItemType{
        FOODS,
        NULL
    }

    @Override
    public String toString() {
        return name;
    }

    public static Item create(String type, String name){
        if (Objects.equals(type,"apple")){
            return new Apple(name);
        }
        else if (Objects.equals(type,"null")) {
            return new NullItem(name);
        }
        else {
            return null;
        }
    }

    public void remove(Item item){
        GameRoll.getItems().remove(item);
    }
}
