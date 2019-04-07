package com.fixthewall.game.actors.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DoubleLabel extends Table {

    private float height;

    public DoubleLabel(BitmapFont font, String left, String right) {
        Label temp = new Label(left, new Label.LabelStyle(font, Color.WHITE));
        this.height = temp.getHeight();
        add(temp);
        add(new Label(right, new Label.LabelStyle(font, Color.WHITE)));
//        setDebug(true);
    }

    @Override
    public float getHeight() {
        return height;
    }
}
