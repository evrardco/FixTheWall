package com.fixthewall.game.actors.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.fixthewall.game.logic.GameLogic;

public class PopupLabel extends Label {

    private ParallelAction parallel;
    private float alpha;
    public PopupLabel(AssetManager ass) {
        super("This is a popup label !", new LabelStyle(ass.get("PoetsenOne30.ttf", BitmapFont.class), Color.BLACK));
        parallel = new ParallelAction();
        parallel.addAction(Actions.moveBy(0f, 150f, 1f));

        setColor(getColor().r, getColor().g, getColor().b, 0f);
        setTouchable(Touchable.disabled); // click through
    }

    public void show(float posX, float posY) {
        this.alpha = 1.0f;
        setPosition(posX, posY);
        setColor(getColor().r, getColor().g, getColor().b, 1f);
        removeAction(parallel); // enlève l'action pour la reset (si on fait juste restart ça suffit pas)
        parallel.restart();
        addAction(parallel);

        GameLogic instance = GameLogic.getSingleInstance();
        setText("+" + instance.getBricksPowerString() + " Bricks, +" + instance.getHealingPowerString() + " HP");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.alpha = Math.max(0.0f, alpha - delta);
        Color c0l1 = this.getColor();
        c0l1.a = alpha;
        this.setColor(c0l1);

    }
}
