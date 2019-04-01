package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fixthewall.game.actors.Nuages;
import com.fixthewall.game.actors.ui.MenuTable;
import com.fixthewall.game.actors.Wall;
import com.fixthewall.game.Game;
import com.fixthewall.game.logic.MusicLogic;


public class StartScreen implements Screen {

    private Stage stage;
    private Game game;

    public StartScreen(final Game game){
        this.game = game;

        //music
        game.playlist = new MusicLogic(game.ass);
        game.playlist.runPlaylist();
        //

        Texture textureFond = game.ass.get("fondWall.png");
        Image imgFond = new Image(textureFond);
        stage = new Stage(game.viewport);

        Nuages nuages = new Nuages(game.ass);

        //setup Button
        ImageTextButton.ImageTextButtonStyle playButtonStyle = new ImageTextButton.ImageTextButtonStyle();
        playButtonStyle.up = new TextureRegionDrawable(game.ass.get("ui/texture_button_play.png", Texture.class));
        playButtonStyle.down = new TextureRegionDrawable(game.ass.get("ui/texture_button_play_down.png", Texture.class));
        playButtonStyle.font = game.ass.get("Germania120.ttf");
        ImageTextButton playButton = new ImageTextButton("", playButtonStyle);

        float x = game.viewport.getWorldWidth() / 2f;
        float y = game.viewport.getWorldHeight() / 2f;
        x = x - playButton.getWidth() / 2;
        y = y - playButton.getHeight() / 2;

        playButton.setPosition(x, y);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new GameScreen(game));
            }
        });

        // Settings menu
        Texture imgButton = game.ass.get("ui/texture_button_settings.png");
        Texture imgButtonDown = game.ass.get("ui/texture_button_settings_down.png");
        Button settingsButton = new ImageButton(new TextureRegionDrawable(imgButton), new TextureRegionDrawable(imgButtonDown));
        settingsButton.setPosition(game.viewport.getWorldWidth() * 0.1f, game.viewport.getWorldHeight() * 0.9f);

        final MenuTable settingsTable = new MenuTable(game.ass, settingsButton.getX(), settingsButton.getY(), settingsButton.getPrefWidth());
        settingsTable.setVisible(false);

        Button volumeButton = new ImageButton(
                new TextureRegionDrawable(game.ass.get("ui/texture_button_volume.png", Texture.class)),
                new TextureRegionDrawable(game.ass.get("ui/texture_button_volume_down.png", Texture.class))
        );
        volumeButton.addListener(new ClickListener() {
            @Override
            public  void clicked(InputEvent event, float x, float y) {
                if (game.playlist.isPlaying())
                    game.playlist.pause();
                else
                    game.playlist.play();
            }
        });
        settingsTable.addEntry(volumeButton);

        settingsButton.addListener(new ClickListener() {
            @Override
            public  void clicked(InputEvent event, float x, float y) {
                settingsTable.setVisible(!settingsTable.isVisible());
            }
        });
        // End settings menu

        stage.addActor(imgFond);
        stage.addActor(nuages);
        Wall wall = new Wall(game.ass);
        stage.addActor(wall);
        //add button to the scene
        stage.addActor(playButton);
        stage.addActor(settingsTable);
        stage.addActor(settingsButton);
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
