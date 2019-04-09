package com.fixthewall.game.upgrades;

import com.badlogic.gdx.Gdx;
import com.fixthewall.game.logic.GameLogic;

public class TrumpUpgrade extends AbstractUpgrade{

        public TrumpUpgrade(int level, double cost) {
            super(level, cost);
            this.setName("Be proud of your savior");
            this.setDesc("Something strange will appear...");
            this.setType(AbstractUpgrade.TYPE_OTHER);
        }
        @Override
        public void apply() {
            super.apply();
            this.setCost(this.getLevel()*10+this.getCost()*Math.log(this.getCost()));
            GameLogic instance = GameLogic.getSingleInstance();
            instance.setTrumpTime(10+5*getLevel());

        }
    }