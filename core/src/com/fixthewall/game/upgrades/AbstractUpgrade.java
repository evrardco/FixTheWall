package com.fixthewall.game.upgrades;

import com.fixthewall.logic.WallLogic;

public abstract class AbstractUpgrade {

    private int level;
    private float base;
    private int cost;


    private boolean applied;

    public AbstractUpgrade(int level, int cost){
        this.level = level;
        this.cost = cost;
        this.applied = false;
    }

    public void levelUp(){
        level++;
        cost = cost*2;
    }

    public void unApply(){

    }

    public void unAplly(){

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public float getBase() {
        return base;
    }

    public void setBase(float base) {
        this.base = base;
    }

    public boolean isApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }





}
