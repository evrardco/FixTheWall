package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.fixthewall.game.logic.GameLogic;

public class PopupLabel extends Label {

    ParallelAction parallel;

    public PopupLabel(AssetManager ass) {
        super("This is a popup label !", new LabelStyle(ass.get("Germania30.ttf", BitmapFont.class), Color.BLACK));
        parallel = new ParallelAction();
        parallel.addAction(Actions.moveBy(0f, 150f, 1f));
        parallel.addAction(Actions.alpha(0f, 1f));
        setColor(getColor().r, getColor().g, getColor().b, 0f);
        setTouchable(Touchable.disabled); // click through
    }

    public void show(float posX, float posY) {
        setPosition(posX, posY);
        setColor(getColor().r, getColor().g, getColor().b, 1f);
        removeAction(parallel); // enlève l'action pour la reset (si on fait juste restart ça suffit pas)
        parallel.restart();
        addAction(parallel);

        GameLogic instance = GameLogic.getSingleInstance();
        setText("+" + (long) instance.getBricksPower() + " Bricks, +" + (long) instance.getHealingPower() + " HP");
    }

}
