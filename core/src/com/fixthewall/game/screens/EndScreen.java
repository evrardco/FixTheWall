package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fixthewall.game.actors.Nuages;
import com.fixthewall.game.actors.Wall;
import com.fixthewall.game.Game;
import com.fixthewall.game.actors.ui.BigMenuTable;
import com.fixthewall.game.actors.ui.DoubleLabel;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.logic.MexicanLogic;
import com.fixthewall.game.upgrades.UpgradeManager;

public class EndScreen implements Screen {

    private final Game game;
    private Stage stage;

    public EndScreen(final Game game, Nuages nuages, Group dayNightCycleGroup, Group dayNightBackground) {
        this.game = game;
        stage = new Stage(game.viewport);

        dayNightBackground.getChildren().get(1).getActions().removeIndex(0);
        stage.addActor(dayNightBackground);

        stage.addActor(dayNightCycleGroup);

        Wall wall = new Wall(game.ass);
        stage.addActor(wall);

        stage.addActor(nuages);

        //Import font
        BitmapFont font = game.ass.get("PoetsenOne50.ttf");

        BigMenuTable finalMenu = new BigMenuTable(game.ass, "You lost!");
        finalMenu.addEntry(new DoubleLabel(font, "High Score: ", GameLogic.getSingleInstance().getHighScoreString()));
        finalMenu.addEntry(new DoubleLabel(font, "Score: ", GameLogic.getSingleInstance().getScoreString()));


        // TODO tester avec une table qui est de width égale à la width du bigmenu
        ImageTextButton.ImageTextButtonStyle restartButtonStyle = new ImageTextButton.ImageTextButtonStyle();
        restartButtonStyle.up = new TextureRegionDrawable(game.ass.get("ui/texture_button.png", Texture.class));
        restartButtonStyle.down = new TextureRegionDrawable(game.ass.get("ui/texture_button_down.png", Texture.class));
        restartButtonStyle.font = font;
        restartButtonStyle.fontColor = Color.BLACK;
        ImageTextButton restartButton = new ImageTextButton("Restart", restartButtonStyle);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameLogic.getSingleInstance().init();
                UpgradeManager.getSingleInstance().init(game.ass);
                MexicanLogic.getSingleInstance().init(1.0, 1.0, 1.0, 1.0, game.ass);
                dispose();
                game.setScreen(new GameScreen(game, null));
            }
        });

        finalMenu.addEntry(restartButton);

        stage.addActor(finalMenu);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose () {
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
