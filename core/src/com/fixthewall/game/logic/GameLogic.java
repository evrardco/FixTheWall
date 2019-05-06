package com.fixthewall.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Timer;
import com.fixthewall.game.Helpers;
import com.fixthewall.game.Perziztancinator;

import java.io.Serializable;

public class GameLogic implements Serializable {

    public static transient final float SAVE_INTERVAL = 60f;
    public static transient final int DAY_NIGHT_CYCLE_LEN = 300; // 10 minutes

    private double health;
    private double maxHealth;
    private double bricks;
    private boolean available;
    private static GameLogic singleInstance = null;
    private double healingPower;
    private float trumpTime;
    private double bricksPower;
    private int hammerLevel;
    private double score;
    private double highScore;
    transient private boolean isPaused;
    transient private boolean isMenuUpgrade;
    private transient Timer timer;
    private boolean cheatMode;
    private double backupBricks; // before cheatmode
    private double dayNightTime;

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
        cheatMode = false;
        hammerLevel = 0;// 0 correspond au premier indice de l'array d'images de marteaux.
        healingPower = 1;
        bricksPower = 1;
        score = 0.0;
        available = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        highScore = 0d;
        trumpTime = 0.0f;
        timer = new Timer();
        isPaused = false;
        isMenuUpgrade = false;
        backupBricks = 0;
        dayNightTime = DAY_NIGHT_CYCLE_LEN / 4f;

        Timer.Task saveTask = new Timer.Task(){
            public void run(){
                Perziztancinator.save();
                Gdx.app.log("GAMELOGIC", "Saving game...");
            }
        };
        timer.scheduleTask(saveTask, 0.0f, SAVE_INTERVAL, -1 );
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
        if (health > getMaxHealth())
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
        if (this.score > this.highScore)
            this.highScore = this.score;
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

    public void setHammerLevel(int lvl){
        if(0<=lvl && lvl<=7){
            this.hammerLevel=lvl;
        }
    }

    public int getHammerLevel(){
        return hammerLevel;
    }

    public boolean isMenuUpgrade() {
        return isMenuUpgrade;
    }

    public void setMenuUpgrade(boolean isMenuUpgrade) {
        this.isMenuUpgrade = isMenuUpgrade;
    }

    public double getHighScore() {
        return highScore;
    }

    public void setHighScore(double highScore) {
        this.highScore = highScore;
    }

    public boolean isAccelerometerAvailable() {return available;}

    public String getHighScoreString() {
        return Helpers.formatBigNumbers(getHighScore());
    }

    public double getDayNightTime() {
        return dayNightTime;
    }

    public void setDayNightTime(double dayNightTime) {
        this.dayNightTime = dayNightTime;
    }

    public boolean isCheatMode() {
        return cheatMode;
    }

    public boolean isDay() {
        return dayNightTime <= DAY_NIGHT_CYCLE_LEN / 2f;
    }

    public void setCheatMode(boolean cheatMode) {
        this.cheatMode = cheatMode;
        if (isCheatMode()) {
            backupBricks = getBricks();
            setBricks(Double.MAX_VALUE);
        } else
            setBricks(backupBricks);
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
        timer.scheduleTask(saveTask, 0.0f, SAVE_INTERVAL, -1 );
    }

    public void nukeReset(){
        maxHealth = 100;
        health = 1.0;
    }

}
