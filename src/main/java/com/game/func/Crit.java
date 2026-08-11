package com.game.func;

public class Crit {
    private double crit;
    private double damage;
    private String id;
    public double getCrit(double damage,double crit){
        this.damage = damage;
        this.crit = crit;
        double critDamage =  damage + crit;
        return critDamage;
    }
}
