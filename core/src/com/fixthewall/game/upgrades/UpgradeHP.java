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
        GameLogic.getSingleInstance().setBricksPower(GameLogic.getSingleInstance().getBricksPower()*2);
        GameLogic.getSingleInstance().setHealingPower(GameLogic.getSingleInstance().getHealingPower()+1.0);
        double costUp = this.getCost()/20.0;
        this.setCost(this.getCost()*2+costUp);
        UpgradeManager.getSingleInstance().increaseLevelUpgradeHammer();
    }
}