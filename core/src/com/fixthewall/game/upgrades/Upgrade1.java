package com.fixthewall.game.upgrades;

import com.badlogic.gdx.Gdx;
import com.fixthewall.logic.WallLogic;

public class Upgrade1 extends AbstractUpgrade {
    public Upgrade1(int level, int cost) {
        super(level, cost);

    }
    @Override
    public void apply() {
        WallLogic Instance = WallLogic.getSingleInstance();
        Instance.setMaxHealth((int) Instance.getMaxHealth()*2);
        Gdx.app.log("Upgrade 1","max: "+Instance.getMaxHealth());

    }
}
