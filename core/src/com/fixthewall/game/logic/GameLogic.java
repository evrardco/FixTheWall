package com.fixthewall.game.logic;

public class GameLogic {
    private double health;
    private double maxHealth;
    private double bricks;
    private static GameLogic singleInstance = null;
    private double healingPower;
    private double bricksPower;

    public static GameLogic getSingleInstance(){
        if(singleInstance == null) singleInstance = new GameLogic();
        return singleInstance;
    }

    private GameLogic(){}

    public void init(){
        this.maxHealth = 100.5f;
        health = maxHealth;
        bricks = 0;
        healingPower = 1.0;
        bricksPower = 1.0;
    }

    public double getHealth() {
        return health;
    }

    public double getHealingPower() {
        return healingPower;
    }

    public void setBricksPower(double bricksPower) {
        this.bricksPower = bricksPower;
    }

    public double getBricksPower() {
        return bricksPower;
    }

    public void setHealingPower(double healingPower) {
        this.healingPower = healingPower;
    }
    public void setHealth(double health) {
        if (health < 0)
            this.health = 0;
        else
            this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getBricks() {
        return bricks;
    }

    public void setBricks(double bricks) {
        this.bricks = bricks;
    }

    public void reduceHealth(double n) {
        health = health - n;
    }


}
