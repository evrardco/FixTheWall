package com.fixthewall.game.upgrades;

import com.fixthewall.game.logic.MexicanLogic;

public class UpgradeCashRain extends AbstractUpgrade {

    public UpgradeCashRain(int level, double cost) {
        super(level, cost);
        this.setName("Cashrain");
        this.setDesc("Pay mexicans and see what\nhappens !");
        this.setType(AbstractUpgrade.TYPE_OTHER);
    }

    @Override
    public void apply() {
        super.apply();
        MexicanLogic.getSingleInstance().setEnnemiToRemove(this.getLevel()*3);
        this.setCost(this.getCost()*2);
        this.setLevel(this.getLevel()+1);
    }
}