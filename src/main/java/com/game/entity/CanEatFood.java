package com.game.entity;

import com.game.item.Food;

public interface CanEatFood {
    default void eat(Food food, Entity entity) {
        food.eat(entity);
        Food.remove(food);
    }
}
