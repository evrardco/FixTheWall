package com.fixthewall.game.logic;

import java.io.Serializable;

public class BadGuysLogic implements Serializable {

    private double damage;
    private double mul;

    private static BadGuysLogic singleInstance = null;

    public static BadGuysLogic getSingleInstance(){
        if(singleInstance == null) singleInstance = new BadGuysLogic();
        return singleInstance;
    }

    private BadGuysLogic(){}

    public void init(double damage, float mul){
        this.damage = damage;
        this.mul = mul;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getMul() {
        return mul;
    }

    public void setMul(float mul) {
        this.mul = mul;
    }

    public void doDamage() {
        GameLogic.getSingleInstance().setHealth(
                 GameLogic.getSingleInstance().getHealth() - damage * mul);
    }
    public void init(BadGuysLogic instance){
        singleInstance = instance;
    }

}
