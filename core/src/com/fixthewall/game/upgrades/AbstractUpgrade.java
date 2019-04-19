package com.fixthewall.game.upgrades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fixthewall.game.logic.GameLogic;

import java.io.Serializable;

public abstract class AbstractUpgrade implements Serializable {

    public static final int TYPE_HAMMER = 0;
    public static final int TYPE_WALL = 1;
    public static final int TYPE_OTHER = 2;

    protected int level;
    private float base;
    protected double cost;
    private String desc;
    private String name;
    private int type;

    protected boolean applied;

    public boolean isAffordable(){
        return (GameLogic.getSingleInstance().getBricks() >= this.cost);
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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
                    applied = true;
                    UpgradeManager.getSingleInstance().update();
                }else{
                    Gdx.app.log("Upgrade", "not enough bricks");
                }
            }
        };

    }

    public void save(){
        String className = this.getClass().getSimpleName();

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
