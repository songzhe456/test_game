package com.game.func;

public class Crit {
    private double crit;
    private double damage;
    public double getCrit(double damage,double crit){
        this.damage = damage;
        this.crit = crit;
        return damage + crit;
    }

    public double getDamage() {
        return damage;
    }

    public double getCrit() {
        return crit;
    }
}
