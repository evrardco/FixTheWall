package com.fixthewall.game.upgrades;

import com.fixthewall.game.logic.GameLogic;

public class Upgrade2 extends AbstractUpgrade {
    public Upgrade2(int level, double cost){
        super(level, cost);
        this.setName("Bricks++");
        this.setDesc("Increases the bricks you gain");

    }
    @Override
    public void apply() {
        super.apply();
        GameLogic.getSingleInstance().setBricksPower(GameLogic.getSingleInstance().getBricksPower()*2);
        this.setCost(this.getCost()*2);
    }
}
