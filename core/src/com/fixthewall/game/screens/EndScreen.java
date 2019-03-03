package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.fixthewall.game.actors.Wall;
import com.fixthewall.game.Game;
import com.fixthewall.game.logic.GameLogic;

public class EndScreen implements Screen {

    private final Game game;
    private Stage stage;
    private Texture textureFond;
    private BitmapFont font;
    private Wall wall;

    public EndScreen(final Game game) {
        this.game = game;
        stage = new Stage(game.viewport);

        textureFond = new Texture("fondWall.png");
        Image imgFond = new Image(textureFond);
        stage.addActor(imgFond);

        wall = new Wall();
        stage.addActor(wall);

        //Import font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Germania.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter); // font size 12 pixels
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        //setup Button
        Button playButton = new TextButton("Restart", style);
        float x = game.viewport.getWorldWidth() / 2f;
        float y = game.viewport.getWorldHeight() / 2f;
        x = x - playButton.getWidth() / 2;
        y = y - playButton.getHeight() / 2;

        playButton.setPosition(x, y);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                GameLogic.getSingleInstance().init();
                dispose();
                game.setScreen(new GameScreen(game));
            }
        });
        //add button to the scene
        stage.addActor(playButton);

        Label trumpLabel = new Label("You Lost!", new Label.LabelStyle(font, Color.BLACK));
        trumpLabel.setPosition(game.viewport.getWorldWidth() / 3f, game.viewport.getWorldHeight() * 0.9f);
        stage.addActor(trumpLabel);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose () {
        wall.dispose();
        textureFond.dispose();
        font.dispose();
        stage.dispose();
    }
    @Override
    public void show() {

    }



    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
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


}
