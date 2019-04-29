package com.fixthewall.game.upgrades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fixthewall.game.actors.Nuke;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.logic.MexicanLogic;

public class NukeUpgrade extends AbstractUpgrade{
    private int launchedCount;
    public NukeUpgrade(int level, double cost) {
        super(level, cost);
        this.setName("Nuke them all !");
        this.setDesc("War... War never changes.\nResets everything but \nthe hammer's healing power !");
        this.setType(AbstractUpgrade.TYPE_OTHER);
        this.setCost(GameLogic.getSingleInstance().getBricks());
        this.launchedCount = 0;
    }
    @Override
    public void apply(){
        this.setCost(GameLogic.getSingleInstance().getBricks()+this.getCost());
    }

    @Override
    public ClickListener getListener(){
        return  new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){

                GameLogic Instance = GameLogic.getSingleInstance();
                if(Instance.getBricks() >= UpgradeManager.getSingleInstance().getAllUpgrade()[7].getCost()) {
                    level++;
                    GameLogic.getSingleInstance().setBricks(GameLogic.getSingleInstance().getBricks()
                            - UpgradeManager.getSingleInstance().getAllUpgrade()[7].getCost());
                    MexicanLogic.getSingleInstance().launchNuke();
                }else{
                    Gdx.app.log("Upgrade", "not enough bricks");
                }
            }
        };

    }
}
