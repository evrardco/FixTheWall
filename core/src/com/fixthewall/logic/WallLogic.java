package com.fixthewall.logic;

public class WallLogic {
    private float health;
    private float maxHealth;
    private int bricks;
    private static WallLogic singleInstance = null;

    public static WallLogic getSingleInstance(){
        if(singleInstance == null) singleInstance = new WallLogic();
        return singleInstance;
    }

    private WallLogic(){}

    public void init(float maxHealth){
        this.maxHealth = maxHealth;
        health = maxHealth;
        bricks = 0;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getBricks() {
        return bricks;
    }

    public void setBricks(int bricks) {
        this.bricks = bricks;
    }


}
