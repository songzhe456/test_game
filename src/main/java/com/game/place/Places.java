package com.game.place;

public enum Places {
    GRASS_LAND("草地"),
    IN_LAVA("熔岩内"),
    SEA("海");

    private final String name;
    Places(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
