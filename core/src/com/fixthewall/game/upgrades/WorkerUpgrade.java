package com.fixthewall.game.upgrades;

import com.badlogic.gdx.assets.AssetManager;
import com.fixthewall.game.actors.Worker;
import com.fixthewall.game.logic.MexicanLogic;

public class WorkerUpgrade extends AbstractUpgrade {

    private AssetManager ass;

    public WorkerUpgrade(int level, double cost, AssetManager ass) {
        super(level, cost);
        this.setName("Recruit");
        this.setDesc("Give an invader a \"better\" life\nby giving him a job to help fix your wall.");
        this.ass = ass;
    }

    @Override
    public void apply() {
        super.apply();
        this.setCost(this.getCost() + this.getCost() * 0.25);
        MexicanLogic.getSingleInstance().getWorkerGroup().addActor(new Worker(ass));
    }
}
