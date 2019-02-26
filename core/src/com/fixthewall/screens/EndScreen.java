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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fixthewall.game.Game;
import com.fixthewall.logic.WallLogic;

import static com.badlogic.gdx.Gdx.app;

public class EndScreen implements Screen {
    private final Game game;
    private Stage stage;
    private SpriteBatch batch;
    private Texture imgFond;
    private BitmapFont font;


    public EndScreen(final Game game) {
        batch = new SpriteBatch();
        stage = new Stage();
        imgFond = new Texture("fondWall.png");
        this.game = game;
        //incrementation de base.

        //Import font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Germania.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        font12.setColor(Color.RED);
        style.font = font12;
        //setup Button
        Button playButton = new TextButton("Restart", style);
        float x = Gdx.graphics.getWidth()/2f;
        float y = Gdx.graphics.getHeight()/2f;
        x = x - playButton.getWidth()/2;
        y = y - playButton.getHeight()/2;

        playButton.setPosition(x, y);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                System.out.print("hahahahahahahahah");
                WallLogic.getSingleInstance().init(100);
                game.setScreen(new GameScreen(game));
            }
        });
        //add button to the scene
        stage.addActor(playButton);

        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgFond, 0, 0);
        font.draw(batch, "Trump lost", Gdx.graphics.getWidth()/3f,
                (float) (Gdx.graphics.getHeight()*0.9));
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose () {
        batch.dispose();
        imgFond.dispose();
        font.dispose();
        stage.dispose();
        game.dispose();
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
