package com.fixthewall.game.upgrades;

import com.fixthewall.game.logic.GameLogic;

public class UpgradeHP extends AbstractUpgrade {
    public UpgradeHP(int level, double cost){
        super(level, cost);
        this.setName("HP++");
        this.setDesc("Increases the HP you gain");
        this.setType(AbstractUpgrade.TYPE_HAMMER);
    }

    @Override
    public void apply() {
        super.apply();
        GameLogic.getSingleInstance().setHealingPower(GameLogic.getSingleInstance().getHealingPower()+4.0);
        //double costUp = this.getCost()/20.0;
        this.setCost(this.getCost()*1.5+this.getLevel()*this.getCost()/120.0);
    }
}