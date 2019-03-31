package com.fixthewall.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.fixthewall.game.Helpers;
import com.fixthewall.game.Perziztancinator;

import java.io.Serializable;

public class GameLogic implements Serializable {
    private double health;
    private double maxHealth;
    private double bricks;
    private static GameLogic singleInstance = null;
    private double healingPower;
    private float trumpTime;
    private double bricksPower;
    private double score;
    private boolean isPaused;
    private transient Timer timer;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public static GameLogic getSingleInstance(){
        if(singleInstance == null) singleInstance = new GameLogic();
        return singleInstance;
    }

    private GameLogic(){}

    public void init(){
        this.maxHealth = 100;
        health = maxHealth;
        bricks = 0;
        healingPower = 1.0;
        bricksPower = 1.0;
        score = 0.0;
        trumpTime = 0.0f;
        timer = new Timer();
        isPaused = false;

        Timer.Task saveTask = new Timer.Task(){
            public void run(){
                Perziztancinator.getSingleInstance().save();
                Gdx.app.log("GAMELOGIC", "Saving game...");
            }
        };
        timer.scheduleTask(saveTask, 0.0f, 1.0f, -1 );
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
        else if (health > getMaxHealth())
            this.health = getMaxHealth();
        else
            this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public float getTrumpTime() {
        return trumpTime;
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
        setHealth(health - n);
    }

    public void setTrumpTime(float time) {
        this.trumpTime = time;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void togglePaused() {
        isPaused = !isPaused;
    }

    public boolean isPaused() {
        return isPaused;
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

    /**
     * Pour sauvegarde
     */
    public void init(GameLogic instance){
        singleInstance = instance;

        timer = new Timer();
        Timer.Task saveTask = new Timer.Task(){
            public void run(){
                Perziztancinator.save();
                Gdx.app.log("GAMELOGIC", "Saving game...");
            }
        };
        timer.scheduleTask(saveTask, 0.0f, 1.0f, -1 );
    }

}
