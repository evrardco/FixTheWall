package com.fixthewall.game.logic;

import com.fixthewall.game.Helpers;

import java.io.Serializable;

public class GameLogic implements Serializable {
    private double health;
    private double maxHealth;
    private double bricks;
    private static GameLogic singleInstance = null;
    private double healingPower;
    private double bricksPower;
    private double score;

    public static GameLogic getSingleInstance(){
        if(singleInstance == null) singleInstance = new GameLogic();
        return singleInstance;
    }

    private GameLogic(){}

    public void init(){
        this.maxHealth = 100.5;
        health = maxHealth;
        bricks = 0;
        healingPower = 1.0;
        bricksPower = 1.0;
        score = 0.0;
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
               setHealth(this.health - n);
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public String getHealthString() {
        return Helpers.formatBigNumbers(getHealth()) + "/" + Helpers.formatBigNumbers(getMaxHealth());
    }

    public String getBricksString() {
        return Helpers.formatBigNumbers(getBricks());
    }

    public String getScoreString() {
        return Helpers.formatBigNumbers(getScore());
    }

    public String getBricksPowerString() {
        return Helpers.formatBigNumbers(getBricksPower());
    }

    public String getHealingPowerString() {
        return Helpers.formatBigNumbers(getHealingPower());
    }
}
