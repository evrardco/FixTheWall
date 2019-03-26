package com.fixthewall.game.upgrades;

import com.fixthewall.game.logic.GameLogic;

public class Upgrade3 extends AbstractUpgrade {

    public Upgrade3 (int level, double cost)
    {
        super(level, cost);
        this.setName("Cashrain");
        this.setDesc("Pay mexicans and see what happens !");

    }

    @Override
    public void apply() {
        super.apply();
        GameLogic.getSingleInstance().setEnnemiRemoval(this.getLevel()*this.getLevel());
        GameLogic.getSingleInstance().setUpgrade3(true);
        this.setCost(this.getCost()*2);
        this.setLevel(this.getLevel()+1);
    }
}
