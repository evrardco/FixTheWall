package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.fixthewall.game.actors.UpgradeButton;
import com.fixthewall.game.actors.Wall;
import com.fixthewall.game.Game;
import com.fixthewall.game.upgrades.UpgradeManager;

public class UpgradeScreen implements Screen {

    private Stage stage;
    private Game game;

    public UpgradeScreen(final Game game){
        this.game = game;
        stage = new Stage(game.viewport);

        Texture textureFond = game.ass.get("fondWall.png");
        Texture imgButton = game.ass.get("ui/texture_button.png");
        Texture imgButtonReturn = game.ass.get("ui/texture_button_return.png");
        Texture imgButtonReturnDown = game.ass.get("ui/texture_button_return_down.png");
        BitmapFont font = game.ass.get("Germania60.ttf");
        Wall wall = new Wall(game.ass);
        Image imgFond = new Image(textureFond);

        //button retour
        TiledDrawable tileButtonReturn = new TiledDrawable(new TextureRegionDrawable(imgButtonReturn));
        TiledDrawable tileButtonReturnDown = new TiledDrawable(new TextureRegionDrawable(imgButtonReturnDown));

        Button playButton = new ImageButton(tileButtonReturn, tileButtonReturnDown);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(GameScreen.gameScreen);
            }
        });
        //button upgrade

        UpgradeButton but = new UpgradeButton(
                game.ass,
                UpgradeManager.getSingleInstance().getAllUpgrade()[0]
        );

        //table setup

        Table table = new Table(); //test
        table.top().left().add(playButton).padBottom(25).row();
        table.setWidth(Game.GAME_WIDTH * 0.9f);
        table.setHeight(Game.GAME_HEIGHT * 0.9f);
        table.setPosition(
                Game.GAME_WIDTH / 2f - table.getWidth() / 2f,
                Game.GAME_HEIGHT / 2f - table.getHeight() / 2f
        );
        table.setTouchable(Touchable.enabled);
        table.setBackground(
                new TextureRegionDrawable(
                        game.ass.get(
                                "ui/white_background.png",
                                Texture.class)
                )
        );
        table.padTop(25).left().add(but);
        table.setDebug(true);

        //testing upgrade button


        stage.addActor(wall);
        stage.addActor(imgFond);
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
        stage.act(delta);
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
    }
}


