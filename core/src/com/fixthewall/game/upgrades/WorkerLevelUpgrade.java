package com.fixthewall.game.upgrades;

import com.fixthewall.game.logic.MexicanLogic;

public class WorkerLevelUpgrade extends AbstractUpgrade {

    public WorkerLevelUpgrade(int level, double cost) {
        super(level, cost);
        this.setName("Worker level++");
        this.setDesc("Give your workers a raise to\nmake them work harder!");
        this.setType(AbstractUpgrade.TYPE_WALL);
    }

    @Override
    public void apply() {
        super.apply();
        this.setCost(this.getCost() + this.getCost() * 10);
        MexicanLogic.getSingleInstance().setHeal(MexicanLogic.getSingleInstance().getHeal() + 1);
        MexicanLogic.getSingleInstance().setBrickPower(MexicanLogic.getSingleInstance().getBrickPower() + 1);
    }

}
