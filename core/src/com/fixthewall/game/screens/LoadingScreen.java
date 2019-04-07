package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.fixthewall.game.Game;
import com.fixthewall.game.actors.LogoGG;

public class LoadingScreen implements Screen {

    private final Game game;
    private final Label loadLabel;
    private final Stage stage;
    private final LogoGG logo;

    public LoadingScreen(final Game game) {
        stage = new Stage(game.viewport);
        this.game = game;
        BitmapFont font = game.ass.get("PoetsenOne_loading.ttf");
        loadLabel = new Label("Loading: 0%", new Label.LabelStyle(font, Color.WHITE));
        stage.addActor(loadLabel);
        //centering label
        loadLabel.setPosition(game.viewport.getWorldWidth() / 2 - loadLabel.getWidth() / 2,
                game.viewport.getWorldHeight() * 0.45f - loadLabel.getHeight() / 2);

        Label studioLabel = new Label("Colin Evrard - Jean-François Sambon - Sina Shafiei - Maxime Postaire", new Label.LabelStyle(font, Color.WHITE));
        studioLabel.setPosition(game.viewport.getWorldWidth() / 2 - studioLabel.getWidth() / 2,
                game.viewport.getWorldHeight() * 0.1f - studioLabel.getHeight() / 2);
        stage.addActor(studioLabel);

        logo = new LogoGG(game.ass);
        logo.setPosition(game.viewport.getWorldWidth() / 2 - logo.getWidth() / 2,
                game.viewport.getWorldHeight() * 0.6f - logo.getHeight() / 2);
        stage.addActor(logo);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (game.ass.update()) {
            game.setScreen(new StartScreen(game));
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float progress = game.ass.getProgress();
        loadLabel.setText("Loading: " + (int) (progress * 100) + "%");
        loadLabel.setSize(loadLabel.getPrefWidth(), loadLabel.getPrefHeight());
        loadLabel.setPosition(game.viewport.getWorldWidth() / 2 - loadLabel.getWidth() / 2,
                game.viewport.getWorldHeight() * 0.45f - loadLabel.getHeight() / 2);
        logo.setProgress(progress);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
