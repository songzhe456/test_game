package com.test.util;

import com.test.entity.Entity;
import com.test.funny.Flaw;

import java.util.Random;

public class RandomUtil {
    private RandomPool randomPool = new RandomPool();
    public double getRandDouble(int lowest,int max){
        return Math.round(Math.random() * max + lowest);
    }
    public int getRandInt(int lowest,int max){
        return (int)(Math.random() * max + lowest);
    }
    public static void getRandEntity(){
        RandomPool.generateRandomEntity();
    }
}
