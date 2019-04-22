package com.fixthewall.game.upgrades;

import com.badlogic.gdx.assets.AssetManager;
import com.fixthewall.game.actors.Worker;
import com.fixthewall.game.logic.MexicanLogic;

public class WorkerUpgrade extends AbstractUpgrade {

    private AssetManager ass;

    public WorkerUpgrade(int level, double cost, AssetManager ass) {
        super(level, cost);
        this.setName("Worker");
        this.setDesc("Give an invader a \"better\" life\nby giving him a job to\nhelp fix your wall.");
        this.setType(AbstractUpgrade.TYPE_WALL);

    }

    @Override
    public void apply() {
        super.apply();
        this.setCost(this.getCost()*5);
        MexicanLogic.getSingleInstance().addWorker();
    }
}
