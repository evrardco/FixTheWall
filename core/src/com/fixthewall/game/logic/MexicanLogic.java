package com.fixthewall.game.logic;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.io.Serializable;

public class MexicanLogic implements Serializable {

    private transient Group ennemiGroup;
    private transient Group workerGroup;

    private double damage;
    private double heal;
    private double brickPower;
    private double mul;

    private static MexicanLogic singleInstance = null;

    public static MexicanLogic getSingleInstance(){
        if(singleInstance == null) singleInstance = new MexicanLogic();
        return singleInstance;
    }

    private MexicanLogic(){}

    public void init(double damage, double heal, double brickPower, double mul){
        this.damage = damage;
        this.heal = heal;
        this.brickPower = brickPower;
        this.mul = mul;
        ennemiGroup = new Group();
        workerGroup = new Group();
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
                 GameLogic.getSingleInstance().getHealth() - damage * mul
        );
    }

    public void doHeal() {
        GameLogic.getSingleInstance().setHealth(
                GameLogic.getSingleInstance().getHealth() + heal
        );
        GameLogic.getSingleInstance().setBricks(
                GameLogic.getSingleInstance().getBricks() + brickPower
        );
        GameLogic.getSingleInstance().setScore(
                GameLogic.getSingleInstance().getScore() + brickPower
        );
    }

    public void init(MexicanLogic instance){
        singleInstance = instance;
    }

    public Group getWorkerGroup() {
        return workerGroup;
    }

    public Group getEnnemiGroup() {
        return ennemiGroup;
    }

    public double getHeal() {
        return heal;
    }

    public void setHeal(double heal) {
        this.heal = heal;
    }


    public double getBrickPower() {
        return brickPower;
    }

    public void setBrickPower(double brickPower) {
        this.brickPower = brickPower;
    }
}
