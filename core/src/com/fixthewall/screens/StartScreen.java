package com.fixthewall.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fixthewall.game.Game;

import java.util.ArrayList;

public class StartScreen implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private Texture img;
    private Texture imgWall;
    private Texture imgFond;


    public StartScreen(final Game game){

        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        imgWall = new Texture("theWall.png");
        imgFond = new Texture("fondWall.png");
        stage = new Stage();



        //Import font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Germania.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
         // don't forget to dispose to avoid memory leaks!

        //create button style
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        font12.setColor(Color.RED);
        style.font = font12;

        //setup Button
        Button playButton = new TextButton("Start", style);
        float x = Gdx.graphics.getWidth()/2;
        float y = Gdx.graphics.getHeight()/2;
        x = x - playButton.getWidth()/2;
        y = y - playButton.getHeight()/2;

        playButton.setPosition(x, y);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });
        //add button to the scene
        stage.addActor(playButton);
        //necessaire pour rendre le bouton clickable
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgFond, 0, 0);
        batch.draw(imgWall, 0, 300);
        batch.end();

        stage.act();
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

        batch.dispose();
        img.dispose();
        imgWall.dispose();
        imgWall.dispose();
    }
}
