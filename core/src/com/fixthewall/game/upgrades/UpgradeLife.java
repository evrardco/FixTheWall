package com.fixthewall.game.upgrades;

import com.fixthewall.game.logic.GameLogic;
import com.badlogic.gdx.Gdx;

public class UpgradeLife extends AbstractUpgrade {
    public UpgradeLife(int level, double cost) {
        super(level, cost);
        this.setName("Life++");
        this.setDesc("Increase your wall's life !");
    }
    @Override
    public void apply() {
        super.apply();
        this.setCost(this.getLevel()*10+this.getCost()*Math.log(this.getCost()));
        GameLogic instance = GameLogic.getSingleInstance();
        double increased = instance.getMaxHealth();
        instance.setMaxHealth(instance.getMaxHealth() + increased);
        Gdx.app.log("Upgrade Life++","max: " + instance.getMaxHealth());
        instance.setHealth(instance.getHealth() + increased);

    }
}
