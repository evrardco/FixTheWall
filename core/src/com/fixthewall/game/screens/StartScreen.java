package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fixthewall.game.Perziztancinator;
import com.fixthewall.game.actors.DayNightBackground;
import com.fixthewall.game.actors.Ennemi;
import com.fixthewall.game.actors.EnnemiBaleze;
import com.fixthewall.game.actors.Moon;
import com.fixthewall.game.actors.Nuages;
import com.fixthewall.game.actors.Sun;
import com.fixthewall.game.actors.Worker;
import com.fixthewall.game.actors.pools.EnnemiBalezePool;
import com.fixthewall.game.actors.pools.EnnemiPool;
import com.fixthewall.game.actors.ui.MenuTable;
import com.fixthewall.game.actors.Wall;
import com.fixthewall.game.Game;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.logic.MexicanLogic;
import com.fixthewall.game.logic.MusicLogic;
import com.fixthewall.game.upgrades.UpgradeManager;


public class StartScreen implements Screen {

    private Stage stage;
    private Game game;

    private Group ennemiGroup;
    private Group workerGroup;

    private int titleClicks; // to enable cheat

    public StartScreen(final Game game, Boolean isBackToMenu){
        this.game = game;
        titleClicks = 0;

        loadSave();
        MexicanLogic.getSingleInstance().setDisabledNPCs(true);

        //music
        if(!isBackToMenu) {
            game.playlist = new MusicLogic(game.ass);
            game.playlist.runPlaylist();
        }
        //

        stage = new Stage(game.viewport);

        if (GameLogic.getSingleInstance().getHealth() <= 0)
            workerGroup = new Group();
        else
            workerGroup = MexicanLogic.getSingleInstance().getWorkerGroup();
        ennemiGroup = MexicanLogic.getSingleInstance().getEnnemiGroup();

        DayNightBackground background = new DayNightBackground(game.ass);
        final Sun sun = new Sun(game.ass);
        sun.setTrumpDisabled(true);
        final Moon moon = new Moon(game.ass);
        MexicanLogic.getSingleInstance().updateTrumpHead(sun, moon);
        final Nuages nuages = new Nuages(game.ass);

        //setup Button
        ImageTextButton.ImageTextButtonStyle playButtonStyle = new ImageTextButton.ImageTextButtonStyle();
        playButtonStyle.up = new TextureRegionDrawable(game.ass.get("ui/texture_button_play.png", Texture.class));
        playButtonStyle.down = new TextureRegionDrawable(game.ass.get("ui/texture_button_play_down.png", Texture.class));
        playButtonStyle.font = game.ass.get("PoetsenOne120.ttf");
        ImageTextButton playButton = new ImageTextButton("", playButtonStyle);

        float x = game.viewport.getWorldWidth() / 2f;
        float y = game.viewport.getWorldHeight() / 2f;
        x = x - playButton.getWidth() / 2;
        y = y - playButton.getHeight() / 2;

        playButton.setPosition(x, y);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                if (GameLogic.getSingleInstance().getHealth() <= 0)
                    game.setScreen(new EndScreen(game, nuages, ennemiGroup));
                else
                    game.setScreen(new GameScreen(game, nuages));
            }
        });

        // Settings menu
        Texture imgButton = game.ass.get("ui/texture_button_settings.png");
        Texture imgButtonDown = game.ass.get("ui/texture_button_settings_down.png");
        final ImageButton settingsButton = new ImageButton(new TextureRegionDrawable(imgButton), new TextureRegionDrawable(imgButtonDown));
        settingsButton.setPosition(game.viewport.getWorldWidth() * 0.1f, game.viewport.getWorldHeight() * 0.9f);

        final MenuTable settingsTable = new MenuTable(game.ass, settingsButton.getX(), settingsButton.getY(), settingsButton.getPrefWidth());
        settingsTable.setVisible(false);

        final ImageButton.ImageButtonStyle volumeButtonOnStyle = new ImageButton.ImageButtonStyle(
                null, null, null,
                new TextureRegionDrawable(game.ass.get("ui/texture_button_volume.png", Texture.class)),
                new TextureRegionDrawable(game.ass.get("ui/texture_button_volume_down.png", Texture.class)),
                null
        );
        final ImageButton.ImageButtonStyle volumeButtonOffStyle = new ImageButton.ImageButtonStyle(
                null, null, null,
                new TextureRegionDrawable(game.ass.get("ui/texture_button_volume_off.png", Texture.class)),
                new TextureRegionDrawable(game.ass.get("ui/texture_button_volume_off_down.png", Texture.class)),
                null
        );

        final ImageButton volumeButton;
        if (game.playlist.isPlaying())
            volumeButton = new ImageButton(volumeButtonOnStyle);
        else
            volumeButton = new ImageButton(volumeButtonOffStyle);

        volumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.playlist.isPlaying()) {
                    game.playlist.pause();
                    volumeButton.setStyle(volumeButtonOffStyle);
                } else {
                    game.playlist.play();
                    volumeButton.setStyle(volumeButtonOnStyle);
                }
            }
        });
        settingsTable.addEntry(volumeButton);

        final ImageButton.ImageButtonStyle deleteSaveButtonStyle = new ImageButton.ImageButtonStyle();
        deleteSaveButtonStyle.up = new TextureRegionDrawable(game.ass.get("ui/texture_button_deletesave.png", Texture.class));
        deleteSaveButtonStyle.down = new TextureRegionDrawable(game.ass.get("ui/texture_button_deletesave_down.png", Texture.class));

        ImageButton deleteSaveButton = new ImageButton(deleteSaveButtonStyle);

        deleteSaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Perziztancinator.delete();
                double highScore = GameLogic.getSingleInstance().getHighScore();
                boolean isCheatMode = GameLogic.getSingleInstance().isCheatMode();
                GameLogic.getSingleInstance().init();
                GameLogic.getSingleInstance().setHighScore(highScore);
                GameLogic.getSingleInstance().setCheatMode(isCheatMode);
                UpgradeManager.getSingleInstance().init(game.ass);
                float bestWaveNumber = MexicanLogic.getSingleInstance().getBestWaveNumber();
                MexicanLogic.getSingleInstance().init(1.0, 1.0, 1.0, 1.0, game.ass);
                MexicanLogic.getSingleInstance().setBestWaveNumber(bestWaveNumber);
                Perziztancinator.getSingleInstance().setGameLoaded(false);
                MexicanLogic.getSingleInstance().updateTrumpHead(sun, moon);
                ennemiGroup.remove();
                ennemiGroup = MexicanLogic.getSingleInstance().getEnnemiGroup();
                stage.addActor(ennemiGroup);
                workerGroup.remove();
                workerGroup = MexicanLogic.getSingleInstance().getWorkerGroup();
                stage.addActor(workerGroup);
            }
        });

        settingsTable.addEntry(deleteSaveButton);

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsTable.setVisible(!settingsTable.isVisible());
            }
        });
        // End settings menu

        // Game Title
        final Label.LabelStyle titleStyle = new Label.LabelStyle(game.ass.get("Western_outline.ttf", BitmapFont.class), null);
        final Label.LabelStyle titleStyleCheatMode = new Label.LabelStyle(game.ass.get("Western_outline_red.ttf", BitmapFont.class), null);
        final Label title = new Label("Fix the Wall!", titleStyle);
        if (GameLogic.getSingleInstance().isCheatMode())
            title.setStyle(titleStyleCheatMode);
        else
            title.setStyle(titleStyle);

        Container<Label> containerTitle = new Container<Label>(title);
        containerTitle.setPosition(stage.getWidth() / 2f, stage.getHeight() * 0.75f - title.getHeight() * 0.75f);
        containerTitle.setTransform(true);
        containerTitle.addAction(Actions.forever(Actions.sequence(
                Actions.scaleTo(1.1f, 1.1f, 0.4f),
                Actions.scaleTo(1, 1, 0.4f)
        )));


        title.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setTitleClicks(titleClicks + 1);

                if (titleClicks >= 4) {
                    GameLogic.getSingleInstance().setCheatMode(!GameLogic.getSingleInstance().isCheatMode());
                    setTitleClicks(0);

                    if (GameLogic.getSingleInstance().isCheatMode())
                        title.setStyle(titleStyleCheatMode);
                    else
                        title.setStyle(titleStyle);
                }
            }
        });

        stage.addActor(background);
        stage.addActor(sun);
        stage.addActor(moon);
        stage.addActor(nuages);
        Wall wall = new Wall(game.ass);
//        wall.lockTexture(5);
        stage.addActor(wall);
        stage.addActor(ennemiGroup);
        stage.addActor(workerGroup);
        stage.addActor(containerTitle);
        //add button to the scene
        stage.addActor(playButton);
        stage.addActor(settingsTable);
        stage.addActor(settingsButton);
        //necessaire pour rendre le bouton clickable
        Gdx.input.setInputProcessor(stage);
    }

    public void loadSave() {
        //We see whether we must load the saved game.
        if (!Perziztancinator.isNewGame()) Perziztancinator.load();
        if (Perziztancinator.isCorrupt() || Perziztancinator.isNewGame()) {
            UpgradeManager.getSingleInstance().init(game.ass);
            GameLogic.getSingleInstance().init();
            MexicanLogic.getSingleInstance().init(1.0, 1.0, 1.0, 1.0, game.ass);
            Perziztancinator.getSingleInstance().init();
            Gdx.app.log("Game", "Done new game.");
        } else {
            UpgradeManager.getSingleInstance().init(Perziztancinator.getSingleInstance().getUpgManager());
            GameLogic.getSingleInstance().init(Perziztancinator.getSingleInstance().getLogic());
            MexicanLogic.init(Perziztancinator.getSingleInstance().getBadLogic(), game.ass);
            Gdx.app.log("Game", "Done loading.");
        }
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

    public void setTitleClicks(int titleClicks) {
        this.titleClicks = titleClicks;
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
