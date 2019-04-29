package com.fixthewall.game.upgrades;

import com.fixthewall.game.logic.GameLogic;

public class UpgradeBricks extends AbstractUpgrade {
    public UpgradeBricks(int level, double cost){
        super(level, cost);
        this.setName("Bricks++");
        this.setDesc("Increases the bricks you gain");
        this.setType(AbstractUpgrade.TYPE_HAMMER);
    }
    @Override
    public void apply() {
        super.apply();
        GameLogic.getSingleInstance().setBricksPower(GameLogic.getSingleInstance().getBricksPower()*2);
        this.setCost(this.getCost()*2.0+this.getLevel()*this.getCost()/110.0);
        UpgradeManager.getSingleInstance().increaseLevelUpgradeHammer();
    }
}
