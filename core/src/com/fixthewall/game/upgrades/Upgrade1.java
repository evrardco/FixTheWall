package com.fixthewall.game.upgrades;

import com.fixthewall.logic.WallLogic;

public class Upgrade1 extends AbstractUpgrade {
    public Upgrade1(int level, int cost) {
        super(level, cost);
    }
    @Override
    public void apply() {
        WallLogic Instance = WallLogic.getSingleInstance();
        Instance.setMaxHealth((int) Instance.getMaxHealth()*2);

    }
}
