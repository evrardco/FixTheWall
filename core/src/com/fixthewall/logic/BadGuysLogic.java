package com.fixthewall.logic;

public class BadGuysLogic {
    private float damagePerFrame;
    private float mul;

    private static BadGuysLogic singleInstance = null;

    public static BadGuysLogic getSingleInstance(){
        if(singleInstance == null) singleInstance = new BadGuysLogic();
        return singleInstance;
    }


    private BadGuysLogic(){}

    public void init(float dpf, float mul){
        damagePerFrame = dpf;
        this.mul = mul;
    }

    public float getDamagePerFrame() {
        return damagePerFrame;
    }

    public void setDamagePerFrame(float damagePerFrame) {
        this.damagePerFrame = damagePerFrame;
    }

    public float getMul() {
        return mul;
    }

    public void setMul(float mul) {
        this.mul = mul;
    }

    public void doDamage(){
        WallLogic.getSingleInstance().setHealth(
                WallLogic.getSingleInstance().getHealth()-damagePerFrame*mul);
    }




}
