package com.game.entity;

import com.game.item.Foods;

public interface CanEatFood {
    default void eat(Foods food, Entity entity){
        food.eat(entity);
        Foods.remove(food);
    }
}
