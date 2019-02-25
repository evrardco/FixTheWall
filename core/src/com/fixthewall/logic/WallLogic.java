package com.fixthewall.logic;

public class WallLogic {
    private int health;
    private int maxHealth;
    private int bricks;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
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

    public WallLogic(int maxHealth){
        health = maxHealth;
        this.maxHealth = maxHealth;
        bricks = 0;

    }
}
