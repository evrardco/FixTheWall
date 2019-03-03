package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.fixthewall.game.actors.Wall;
import com.fixthewall.game.Game;
import com.fixthewall.game.upgrades.AbstractManager;

public class UpgradeScreen implements Screen {

    private Stage stage;
    private Texture textureFond;
    private Game game;
    private Texture imgButton;
    private Texture imgButtonReturn;
    private Texture imgButtonReturnDown;
    private Wall wall;

    public UpgradeScreen(final Game game){

        this.game = game;
        textureFond = new Texture("fondWall.png");
        imgButton = new Texture("texture_button.png");
        imgButtonReturn = new Texture("texture_button_return.png");
        imgButtonReturnDown = new Texture("texture_button_return_down.png");

        stage = new Stage(game.viewport);
        Image imgFond = new Image(textureFond);
        stage.addActor(imgFond);

        wall = new Wall();
        stage.addActor(wall);

        //Import font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Germania.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();// don't forget to dispose to avoid memory leaks!

        //setup Buttons
        //button upgrade
        TiledDrawable tileButton = new TiledDrawable(new TextureRegionDrawable(imgButton));
        TiledDrawable tileButtonReturn = new TiledDrawable(new TextureRegionDrawable(imgButtonReturn));
        TiledDrawable tileButtonReturnDown = new TiledDrawable(new TextureRegionDrawable(imgButtonReturnDown));
        ImageTextButton.ImageTextButtonStyle imStyle =
                new ImageTextButton.ImageTextButtonStyle(tileButton, tileButton, tileButton, font12);
        Button upgradeTestButton = new ImageTextButton("Upgrade", imStyle);


        Button playButton = new ImageButton(tileButtonReturn, tileButtonReturnDown);


        upgradeTestButton.addListener(AbstractManager.getSingleInstance().getAllUpgrade()[0].getListener());

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new GameScreen(game));
            }
        });

        upgradeTestButton.scaleBy(0.25f);

        Table table = new Table();
        table.add(playButton);
        table.add(upgradeTestButton);
        float x = game.viewport.getWorldWidth() / 2f;
        float y = game.viewport.getWorldHeight() * 0.95f - table.getPrefHeight();
        table.setPosition(x, y);

        stage.addActor(table);

        //necessaire pour rendre le bouton clickable
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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

    @Override
    public void dispose() {
        stage.dispose();
        imgButton.dispose();
        imgButtonReturn.dispose();
        imgButtonReturnDown.dispose();
        textureFond.dispose();
        wall.dispose();
    }
}


