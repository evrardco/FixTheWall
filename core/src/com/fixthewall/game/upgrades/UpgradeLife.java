package com.fixthewall.game.upgrades;

import com.fixthewall.game.logic.GameLogic;
import com.badlogic.gdx.Gdx;

public class UpgradeLife extends AbstractUpgrade {
    public UpgradeLife(int level, double cost) {
        super(level, cost);
        this.setName("Life++");
        this.setDesc("Increase your wall's life !");
        this.setType(AbstractUpgrade.TYPE_WALL);
    }

    @Override
    public void apply() {
        super.apply();
        this.setCost(this.getCost()*2.3+this.getLevel()*this.getCost()/80.0);
        GameLogic instance = GameLogic.getSingleInstance();
        double increased = instance.getMaxHealth();
        instance.setMaxHealth(instance.getMaxHealth() + increased);
        Gdx.app.log("Upgrade Life++","max: " + instance.getMaxHealth());
        instance.setHealth(instance.getHealth() + increased);

    }
}
