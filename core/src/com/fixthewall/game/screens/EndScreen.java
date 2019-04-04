package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    public EndScreen(final Game game) {
        this.game = game;
        stage = new Stage(game.viewport);

        Texture textureFond = game.ass.get("fondWall.png");
        Image imgFond = new Image(textureFond);
        stage.addActor(imgFond);

        Wall wall = new Wall(game.ass);
        stage.addActor(wall);

        Nuages nuages = new Nuages(game.ass);
        stage.addActor(nuages);

        //Import font
        BitmapFont font = game.ass.get("Germania60.ttf");

        BigMenuTable finalMenu = new BigMenuTable(game.ass, "You lost!");
        finalMenu.addEntry(new DoubleLabel(font, "High Score: ", GameLogic.getSingleInstance().getHighScoreString()));
        finalMenu.addEntry(new DoubleLabel(font, "Score: ", GameLogic.getSingleInstance().getScoreString()));

        ImageTextButton.ImageTextButtonStyle upgradeButtonStyle = new ImageTextButton.ImageTextButtonStyle();
        upgradeButtonStyle.up = new TextureRegionDrawable(game.ass.get("ui/texture_button.png", Texture.class));
        upgradeButtonStyle.down = new TextureRegionDrawable(game.ass.get("ui/texture_button_down.png", Texture.class));
        upgradeButtonStyle.font = font;
        upgradeButtonStyle.fontColor = Color.BLACK;
        ImageTextButton restartButton = new ImageTextButton("Restart", upgradeButtonStyle);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameLogic.getSingleInstance().init();
                UpgradeManager.getSingleInstance().init(game.ass);
                MexicanLogic.getSingleInstance().init(1.0, 1.0, 1.0, 1.0, game.ass);
                dispose();
                game.setScreen(new GameScreen(game));
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
