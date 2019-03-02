package com.fixthewall.logic;

public class WallLogic {
    private double health;
    private double maxHealth;
    private int bricks;
    private static WallLogic singleInstance = null;
    private float fixingSpeed;





    public static WallLogic getSingleInstance(){
        if(singleInstance == null) singleInstance = new WallLogic();
        return singleInstance;
    }

    private WallLogic(){}

    public void init(float maxHealth){
        this.maxHealth = maxHealth;
        health = maxHealth;
        bricks = 0;
        fixingSpeed = 1.0f;
    }

    public double getHealth() {
        return health;
    }

    public float getFixingSpeed() {
        return fixingSpeed;
    }

    public void setFixingSpeed(float fixingSpeed) {
        this.fixingSpeed = fixingSpeed;
    }
    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getBricks() {
        return bricks;
    }

    public void setBricks(int bricks) {
        this.bricks = bricks;
    }


}
