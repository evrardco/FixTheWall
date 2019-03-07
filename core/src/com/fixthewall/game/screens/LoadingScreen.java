package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.fixthewall.game.Game;

public class LoadingScreen implements Screen {

    private final Game game;
    private final Label loadLabel;
    private final Stage stage;

    public LoadingScreen(final Game game) {
        stage = new Stage(game.viewport);
        this.game = game;
        BitmapFont font = game.ass.get("GermaniaLoading.ttf");
        loadLabel = new Label("Progress: 0/100", new Label.LabelStyle(font, Color.WHITE));
        stage.addActor(loadLabel);
        //centering label
        loadLabel.setPosition(game.viewport.getWorldWidth()/2 - loadLabel.getWidth()/2,
                game.viewport.getWorldHeight()/2 - loadLabel.getHeight()/2);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {



        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float progress = game.ass.getProgress();
        loadLabel.setText("Progress: "+(int)progress*100+"/100");
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if (game.ass.update()) {
            // c'est loadé, changer le screen

            game.setScreen(new StartScreen(game));
        }


        // afficher le logo de chargement
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
