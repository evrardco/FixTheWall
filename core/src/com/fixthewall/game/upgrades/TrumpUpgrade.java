package com.fixthewall.game.upgrades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.logic.MexicanLogic;

public class TrumpUpgrade extends AbstractUpgrade{

        public TrumpUpgrade(int level, double cost) {
            super(level, cost);
            this.setName("Be proud of your savior");
            this.setDesc("Something strange will appear...\nOnly apply if it's day");
            this.setType(AbstractUpgrade.TYPE_OTHER);
        }
        @Override
        public void apply() {
            super.apply();
            this.setCost(this.getLevel()*this.getCost()*Math.log(this.getCost()));
            GameLogic instance = GameLogic.getSingleInstance();
            instance.setTrumpTime(10+5*getLevel());

        }

    @Override
    public ClickListener getListener(){
        return  new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                GameLogic Instance = GameLogic.getSingleInstance();
                if(Instance.getBricks() >= cost && GameLogic.getSingleInstance().isDay()) {
                    GameLogic.getSingleInstance().setBricks(GameLogic.getSingleInstance().getBricks() - cost);
                    level++;
                    applied = true;
                    UpgradeManager.getSingleInstance().update();
                }else{
                    Gdx.app.log("Upgrade", "not enough bricks");
                }
            }
        };

    }
    }