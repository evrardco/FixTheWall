package com.fixthewall.game.actors;

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

        BitmapFont font30 = ass.get("Germania30.ttf");
        Label label = new Label("Health", new Label.LabelStyle(font30, Color.BLACK));
        label.setPosition(healthBar.getWidth() / 2f - label.getWidth() / 2f,
                healthBar.getHeight() + 10);
        addActor(label);

        BitmapFont font30White = ass.get("GermaniaLoading.ttf");
        health = new Label("100/100", new Label.LabelStyle(font30White, Color.WHITE));
        health.setPosition(healthBar.getWidth() / 2f - health.getWidth() / 2f,
                healthBar.getHeight() / 2f - health.getHeight() / 2f);
        addActor(health);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        healthBar.setValue((float) (GameLogic.getSingleInstance().getHealth() / GameLogic.getSingleInstance().getMaxHealth()));

        health.setText((int) GameLogic.getSingleInstance().getHealth() + "/" + (int) GameLogic.getSingleInstance().getMaxHealth());
        health.setSize(health.getPrefWidth(), health.getPrefHeight());
        health.setPosition(healthBar.getWidth() / 2f - health.getWidth() / 2f,
                healthBar.getHeight() / 2f - health.getHeight() / 2f);
    }

}
