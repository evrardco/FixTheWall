package com.fixthewall.game.actors.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fixthewall.game.logic.GameLogic;

public class HealthBar extends Group {

    private Label health;
    private ProgressBar healthBar;
    private final float BAR_WIDTH = 350f;

    public HealthBar(AssetManager ass) {
        super();

        Texture background = ass.get("ui/texture_progressbar_background.png");
        Texture bar = ass.get("ui/texture_progressbar.png");
        ProgressBar.ProgressBarStyle healthBarStyle = new ProgressBar.ProgressBarStyle(
                new TextureRegionDrawable(background),
                new TextureRegionDrawable(bar)
        );
        healthBarStyle.knobBefore = healthBarStyle.knob;
        healthBar = new ProgressBar(0f, 1f, 1f / BAR_WIDTH, false, healthBarStyle);
        healthBar.setWidth(BAR_WIDTH);
        addActor(healthBar);

        BitmapFont font30Outline = ass.get("PoetsenOne30_outline.ttf");
        Label label = new Label("Health", new Label.LabelStyle(font30Outline, null));
        label.setPosition(0,
                healthBar.getHeight() + 10);
        addActor(label);

        BitmapFont font30 = ass.get("PoetsenOne30.ttf");
        health = new Label("100/100", new Label.LabelStyle(font30, Color.WHITE));
        health.setPosition(healthBar.getWidth() / 2f - health.getWidth() / 2f,
                healthBar.getHeight() / 2f - health.getHeight() / 2f);
        addActor(health);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        healthBar.setValue((float) (GameLogic.getSingleInstance().getHealth() / GameLogic.getSingleInstance().getMaxHealth()));

        health.setText(GameLogic.getSingleInstance().getHealthString());
        health.setSize(health.getPrefWidth(), health.getPrefHeight());
        health.setPosition(healthBar.getWidth() / 2f - health.getWidth() / 2f,
                healthBar.getHeight() / 2f - health.getHeight() / 2f);
    }

}
