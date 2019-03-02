package com.fixthewall.logic;

public class BadGuysLogic {

    private double damagePerSec;
    private double mul;

    private static BadGuysLogic singleInstance = null;

    public static BadGuysLogic getSingleInstance(){
        if(singleInstance == null) singleInstance = new BadGuysLogic();
        return singleInstance;
    }

    private BadGuysLogic(){}

    public void init(double dpf, float mul){
        damagePerSec = dpf;
        this.mul = mul;
    }

    public double getDamagePerSec() {
        return damagePerSec;
    }

    public void setDamagePerFrame(double damagePerSec) {
        this.damagePerSec = damagePerSec;
    }

    public double getMul() {
        return mul;
    }

    public void setMul(float mul) {
        this.mul = mul;
    }

    public void doDamage(double delta) {
        GameLogic.getSingleInstance().setHealth(
                 GameLogic.getSingleInstance().getHealth() - (damagePerSec * mul * delta));
    }

}
