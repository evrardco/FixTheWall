package com.fixthewall.game.upgrades;

import com.fixthewall.game.logic.GameLogic;

public class Upgrade4 extends AbstractUpgrade {
    public Upgrade4(int level, double cost){
        super(level, cost);
        this.setName("HP++");
        this.setDesc("Increases the HP you gain");

    }
    @Override
    public void apply() {
        super.apply();
        GameLogic.getSingleInstance().setBricksPower(GameLogic.getSingleInstance().getBricksPower()*2);
        GameLogic.getSingleInstance().setHealingPower(GameLogic.getSingleInstance().getHealingPower()+1.0);
        double costUp = this.getCost()/20.0;
        this.setCost(this.getCost()+costUp);
    }
}