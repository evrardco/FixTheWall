package com.fixthewall.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fixthewall.game.Game;

public class GameScreen implements Screen {
    SpriteBatch batch;
    Texture img;
    Texture imgWall;
    Texture imgFond;


    public GameScreen(Game game) {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        imgWall = new Texture("theWall.png");
        imgFond = new Texture("fondWall.png");
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgFond, 0, 0);
        batch.draw(imgWall, 0, 300);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
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
