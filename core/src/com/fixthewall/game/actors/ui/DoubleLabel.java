package com.fixthewall.game.actors.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class DoubleLabel extends Table {

    private float height;

    public DoubleLabel(BitmapFont font, String left, String right) {
        Label temp = new Label(left, new Label.LabelStyle(font, Color.WHITE));
        this.height = temp.getHeight();
        top();
        add(temp).align(Align.left).expand().uniform();
        add(new Label(right, new Label.LabelStyle(font, Color.WHITE))).right();
//        setDebug(true);
    }

    @Override
    public float getHeight() {
        return height;
    }
}
