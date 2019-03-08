package com.fixthewall.game.upgrades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fixthewall.game.logic.GameLogic;

public abstract class AbstractUpgrade {

    private int level;
    private float base;
    private double cost;


    private boolean applied;

    public AbstractUpgrade(int level, double cost){
        this.level = level;
        this.cost = cost;
        this.applied = false;
    }

    public ClickListener getListener(){
        return  new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){

                GameLogic Instance = GameLogic.getSingleInstance();
                if(Instance.getBricks() >= cost) {
                    GameLogic.getSingleInstance().setBricks(GameLogic.getSingleInstance().getBricks() - cost);
                    level++;
                    setCost(level*10+cost*Math.log(cost));
                    applied = true;
                    UpgradeManager.getSingleInstance().update();
                }else{
                    Gdx.app.log("Upgrade", "not enough bricks");
                }
            }
        };

    }

    public void levelUp(){
        level++;
        cost = cost*2;
    }

    public void unApply(){

    }

    public void apply(){

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
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
