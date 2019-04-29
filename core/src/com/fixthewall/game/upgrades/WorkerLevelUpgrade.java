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
        this.setCost(this.getCost()*5.0 + this.getCost()/2.0);
        MexicanLogic.getSingleInstance().setHeal(MexicanLogic.getSingleInstance().getHeal() + MexicanLogic.getSingleInstance().getHeal() * 0.25);
        MexicanLogic.getSingleInstance().setBrickPower(MexicanLogic.getSingleInstance().getBrickPower() + MexicanLogic.getSingleInstance().getBrickPower() * 0.25);
    }

}
