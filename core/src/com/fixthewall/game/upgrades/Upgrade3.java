package com.fixthewall.game.upgrades;

import com.fixthewall.game.logic.MexicanLogic;

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
        MexicanLogic.getSingleInstance().setEnnemiToRemove(this.getLevel()*3);
        this.setCost(this.getCost()*2);
        this.setLevel(this.getLevel()+1);
    }
}
