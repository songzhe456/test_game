package com.game.util;

public class RandomUtil {
    //此方法需传入一个最小值和一个最大值取随机浮点数
    public static double getRandDouble(int lowest,int max){
        return Math.round(Math.random() * max + lowest);
    }
    //同上，但是返回值为整数
    public static int getRandInt(int lowest,int max){
        return (int)(Math.random() * max + lowest);
    }

    @Deprecated
    public static void getRandEntity(){
        RandomPool.generateRandomEntity();
    }
}
