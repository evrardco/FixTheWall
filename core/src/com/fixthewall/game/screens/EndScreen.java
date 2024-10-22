package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fixthewall.game.Perziztancinator;
import com.fixthewall.game.actors.DayNightBackground;
import com.fixthewall.game.actors.Moon;
import com.fixthewall.game.actors.Nuages;
import com.fixthewall.game.actors.Sun;
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

    public EndScreen(final Game game, Nuages nuages, Group ennemiGroup) {
        this.game = game;
        stage = new Stage(game.viewport);

        DayNightBackground dayNightBackground = new DayNightBackground(game.ass);
        stage.addActor(dayNightBackground);

        Sun sun = new Sun(game.ass);
        Moon moon = new Moon(game.ass);
        stage.addActor(sun);
        stage.addActor(moon);
        MexicanLogic.getSingleInstance().updateTrumpHead(sun, moon);

        Wall wall = new Wall(game.ass);
        stage.addActor(wall);

        MexicanLogic.getSingleInstance().setDisabledNPCs(true);

        stage.addActor(ennemiGroup);

        stage.addActor(nuages);

        //Import font
        BitmapFont font = game.ass.get("PoetsenOne50.ttf");

        BigMenuTable finalMenu = new BigMenuTable(game.ass, "You lost!");
        finalMenu.addEntry(new DoubleLabel(font, "High Score: ", GameLogic.getSingleInstance().getHighScoreString()));
        finalMenu.addEntry(new DoubleLabel(font, "Score: ", GameLogic.getSingleInstance().getScoreString()));
        finalMenu.addEntry(new DoubleLabel(font, "Best wave: ", "" + (int) MexicanLogic.getSingleInstance().getBestWaveNumber()));
        finalMenu.addEntry(new DoubleLabel(font, "Current wave: ", "" + (int) MexicanLogic.getSingleInstance().getWaveNumber()));

        ImageTextButton.ImageTextButtonStyle restartButtonStyle = new ImageTextButton.ImageTextButtonStyle();
        restartButtonStyle.up = new TextureRegionDrawable(game.ass.get("ui/texture_button.png", Texture.class));
        restartButtonStyle.down = new TextureRegionDrawable(game.ass.get("ui/texture_button_down.png", Texture.class));
        restartButtonStyle.font = font;
        restartButtonStyle.fontColor = Color.BLACK;
        ImageTextButton restartButton = new ImageTextButton("Restart", restartButtonStyle);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                double highScore = GameLogic.getSingleInstance().getHighScore();
                GameLogic.getSingleInstance().init();
                GameLogic.getSingleInstance().setHighScore(highScore);
                UpgradeManager.getSingleInstance().init();
                float bestWaveNumber = MexicanLogic.getSingleInstance().getBestWaveNumber();
                MexicanLogic.getSingleInstance().init(1.0, 1.0, 1.0, 1.0, game.ass);
                MexicanLogic.getSingleInstance().setBestWaveNumber(bestWaveNumber);
                dispose();
                game.setScreen(new GameScreen(game, null));
                Perziztancinator.getSingleInstance().setGameLoaded(false);
            }
        });

        finalMenu.addEntry(restartButton);

        finalMenu.setColor(finalMenu.getColor().r, finalMenu.getColor().g, finalMenu.getColor().b, 0);
        finalMenu.addAction(
                Actions.moveTo(finalMenu.getX(), finalMenu.getY(), 0.5f, Interpolation.pow2Out)
        );
        finalMenu.setPosition(finalMenu.getX(), stage.getHeight() / 2f);

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
