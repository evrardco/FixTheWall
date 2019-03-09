package com.fixthewall.game.upgrades;

import com.fixthewall.game.logic.GameLogic;
import com.badlogic.gdx.Gdx;

public class Upgrade1 extends AbstractUpgrade {
    public Upgrade1(int level, int cost) {
        super(level, cost);
    }
    @Override
    public void apply() {
        GameLogic instance = GameLogic.getSingleInstance();
        instance.setMaxHealth((int) instance.getMaxHealth() * 2);
        Gdx.app.log("Upgrade 1","max: " + instance.getMaxHealth());
        instance.setHealth(instance.getHealth() * 2);

    }
}
