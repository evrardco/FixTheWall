package com.fixthewall.logic;

public class WallLogic {
    private static int health = 50;
    private static int maxHealth;
    private static int bricks;

    public static boolean doLogic() {
        health--;
        return health == 0;
    }

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
