package com.fixthewall.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.fixthewall.game.Game;

public class GameScreen implements Screen {
    private final Game game;
    private SpriteBatch batch;
    private Texture imgWall;
    private Texture imgFond;
    private BitmapFont font;


    public GameScreen(final Game game) {
        batch = new SpriteBatch();
        imgWall = new Texture("theWall.png");
        imgFond = new Texture("fondWall.png");
        this.game = game;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Germania.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!


    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgFond, 0, 0);
        batch.draw(imgWall, 0, 300);
        font.draw(batch, "Bricks: "+game.wallLogic.getBricks(), Gdx.graphics.getWidth()/2,
                (float) (Gdx.graphics.getHeight()*0.9));
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        imgWall.dispose();
        imgWall.dispose();
    }
    @Override
    public void show() {

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


}
