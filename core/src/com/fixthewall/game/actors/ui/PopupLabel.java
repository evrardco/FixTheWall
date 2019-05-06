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

    private float alpha;
    private float startY;

    public PopupLabel(AssetManager ass) {
        super("This is a popup label !", new LabelStyle(ass.get("PoetsenOne30.ttf", BitmapFont.class), Color.BLACK));

        setColor(getColor().r, getColor().g, getColor().b, 0f);
        setTouchable(Touchable.disabled); // click through
    }

    public void show(float posX, float posY) {
        this.alpha = 1.0f;
        setPosition(posX, posY);
        setColor(getColor().r, getColor().g, getColor().b, 1f);

        startY = posY;

        GameLogic instance = GameLogic.getSingleInstance();
        setText("+" + instance.getBricksPowerString() + " Bricks, +" + instance.getHealingPowerString() + " HP");
    }

    @Override
    public void act(float delta) {
        if (GameLogic.getSingleInstance().isMenuUpgrade()) return;
        if (this.getY() >= startY + 150) return;
        super.act(delta);
        this.alpha = Math.max(0.0f, alpha - delta);
        Color c0l1 = this.getColor();
        c0l1.a = alpha;
        this.setColor(c0l1);

        this.setPosition(getX(), getY() + 150 * delta);
    }
}
