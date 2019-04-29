package com.fixthewall.game.upgrades;

import com.badlogic.gdx.assets.AssetManager;
import com.fixthewall.game.actors.Worker;
import com.fixthewall.game.logic.MexicanLogic;

public class WorkerUpgrade extends AbstractUpgrade {


    public WorkerUpgrade(int level, double cost) {
        super(level, cost);
        this.setName("Worker");
        this.setDesc("Give an invader a \"better\" life\nby giving him a job to\nhelp fix your wall.");
        this.setType(AbstractUpgrade.TYPE_WALL);

    }

    @Override
    public void apply() {
        super.apply();
        this.setCost(this.getCost()*2.2+this.getLevel()*this.getCost()/50.0);
        MexicanLogic.getSingleInstance().addWorker();
    }
}
