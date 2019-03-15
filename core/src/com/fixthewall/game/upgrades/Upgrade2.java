package com.fixthewall.game.upgrades;

import com.fixthewall.game.actors.Hammer;
import com.fixthewall.game.logic.GameLogic;

public class Upgrade2 extends AbstractUpgrade {
    public Upgrade2(){
        super(0, 100);
        this.setName("Bricks++");
        this.setDesc("Increases the brics you gain");

    }
    @Override
    public void apply() {
        super.apply();
        GameLogic.getSingleInstance().setBricksPower(
                GameLogic.getSingleInstance().getBricksPower()*2
        );
        this.setCost(this.getCost()*2+1);
    }
}
