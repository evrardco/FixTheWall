package com.fixthewall.game.upgrades;

import com.fixthewall.logic.GameLogic;
import com.badlogic.gdx.Gdx;

public class Upgrade1 extends AbstractUpgrade {
    public Upgrade1(int level, int cost) {
        super(level, cost);
    }
    @Override
    public void apply() {
        GameLogic Instance = GameLogic.getSingleInstance();
        Instance.setMaxHealth((int) Instance.getMaxHealth() * 2);
        Gdx.app.log("Upgrade 1","max: " + Instance.getMaxHealth());

    }
}
