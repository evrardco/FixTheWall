package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fixthewall.game.Perziztancinator;
import com.fixthewall.game.actors.DayNightBackground;
import com.fixthewall.game.actors.Dynamite;
import com.fixthewall.game.actors.Moon;
import com.fixthewall.game.actors.Sun;
import com.fixthewall.game.actors.ui.BigMenuTable;
import com.fixthewall.game.actors.ui.HealthBar;
import com.fixthewall.game.actors.Nuages;
import com.fixthewall.game.actors.ui.PopupLabel;
import com.fixthewall.game.actors.ui.UpgradeButton;
import com.fixthewall.game.actors.Wall;
import com.fixthewall.game.Game;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.actors.Hammer;
import com.fixthewall.game.logic.MexicanLogic;
import com.fixthewall.game.upgrades.AbstractUpgrade;
import com.fixthewall.game.upgrades.UpgradeManager;

import java.util.LinkedList;

public class GameScreen implements Screen {

    private final int MAX_POPUP_LABELS = 10;

    private final Game game;
    private Stage stage;
    private Hammer hammer;
    private Image pause;
    private Image pauseFond;
    private Label bricksLabel;
    private Label scoreLabel;
    private Label waveLabel;
    private Group dollarGroup;
    private Group nukeExplosionGroup;
    private Group ennemiGroup;
    private Group workerGroup;
    private Dynamite dynamite;
    private Group dayNightCycleGroup;
    private Sun trump;
    private Moon moon;
    private Nuages nuages;

    private boolean speedyMode;

    private LinkedList<PopupLabel> popupLabels;

    public GameScreen(final Game game, Nuages nuages) {
        this.game = game;
        stage = new Stage(game.viewport);
        speedyMode = false;

        MexicanLogic.getSingleInstance().setDisabledNPCs(false);

        DayNightBackground background = new DayNightBackground(game.ass);

        moon = new Moon(game.ass);
        trump = new Sun(game.ass);

        if (nuages == null)
            this.nuages = new Nuages(game.ass);
        else
            this.nuages = nuages;

        final Wall wall = new Wall(game.ass);
        dynamite = new Dynamite(game.ass);
        Group dynamiteGroup = new Group();
        dynamiteGroup.addActor(dynamite);
        hammer = new Hammer(game.ass);
        dayNightCycleGroup = MexicanLogic.getSingleInstance().getDayNightCycleGroup();
        dayNightCycleGroup.addActor(moon);
        dayNightCycleGroup.addActor(trump);
        nukeExplosionGroup = MexicanLogic.getSingleInstance().getNukeExplosionGroup();

        ImageTextButton.ImageTextButtonStyle backButtonStyle = new ImageTextButton.ImageTextButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(game.ass.get("ui/texture_button.png", Texture.class));
        backButtonStyle.down = new TextureRegionDrawable(game.ass.get("ui/texture_button_down.png", Texture.class));
        backButtonStyle.font = game.ass.get("PoetsenOne50.ttf");
        backButtonStyle.fontColor = Color.BLACK;
        final ImageTextButton backButton = new ImageTextButton("Back to menu", backButtonStyle);
        backButton.setPosition(0.5f * stage.getWidth() - backButton.getWidth() / 2f, 0.15f * stage.getHeight() - backButton.getHeight());
        backButton.setVisible(false);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Perziztancinator.save();
                dispose();
                game.setScreen(new StartScreen(game));
            }
        });

        pause = new Image(game.ass.get("imgPause.png", Texture.class));
        pause.setPosition(stage.getWidth() * 0.05f, stage.getHeight() * 0.95f - pause.getHeight() / 2f);
        pauseFond = new Image(game.ass.get("imgPauseFond.png", Texture.class));
        pauseFond.addListener(new ClickListener() {
            @Override
            public  void clicked(InputEvent event, float x, float y){
                GameLogic.getSingleInstance().togglePaused();
                Dynamite.onPause = GameLogic.getSingleInstance().isPaused();
                pause.setVisible(true);
                pauseFond.setVisible(false);
                backButton.setVisible(false);
            }
        });
        pauseFond.setVisible(false);

        ennemiGroup = MexicanLogic.getSingleInstance().getEnnemiGroup();
        workerGroup = MexicanLogic.getSingleInstance().getWorkerGroup();
        Group laserGroup = MexicanLogic.getSingleInstance().getLaserGroup();
        Group brixplosionGroup = MexicanLogic.getSingleInstance().getBrixplosionGroup();
        dollarGroup = new Group();
        dollarGroup.getChildren().ensureCapacity(256);
        Group hammerGroup = new Group();
        hammer = new Hammer(game.ass);

        //Initializing upgrade menu
        final BigMenuTable menuUpgrade = new BigMenuTable(game.ass, "Upgrades", true, false);

        AbstractUpgrade[] upArray = UpgradeManager.getSingleInstance().getAllUpgrade();
        for(int i=0; i < upArray.length; i++) {
            menuUpgrade.addEntry(new UpgradeButton(game.ass, upArray[i]));
        }

        //Hammer grouping
        popupLabels = new LinkedList<PopupLabel>();
        for (int i = 0; i < MAX_POPUP_LABELS; i++) {
            PopupLabel temp = new PopupLabel(game.ass);
            popupLabels.add(temp);
            hammerGroup.addActor(temp);
        }
        hammerGroup.addActor(hammer);

        //Import font

        BitmapFont font = game.ass.get("PoetsenOne30.ttf"); // font size 12 pixels
        BitmapFont fontUps = game.ass.get("PoetsenOne50.ttf");
        BitmapFont fontOutline = game.ass.get("PoetsenOne30_outline.ttf");
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = fontUps;
        //Setting up listeners

        ImageTextButton.ImageTextButtonStyle upgradeButtonStyle = new ImageTextButton.ImageTextButtonStyle();
        upgradeButtonStyle.up = new TextureRegionDrawable(game.ass.get("ui/texture_button.png", Texture.class));
        upgradeButtonStyle.down = new TextureRegionDrawable(game.ass.get("ui/texture_button_down.png", Texture.class));
        upgradeButtonStyle.font = game.ass.get("PoetsenOne50.ttf");
        upgradeButtonStyle.fontColor = Color.BLACK;
        ImageTextButton upgradeButton = new ImageTextButton("Upgrades", upgradeButtonStyle);

        float x = 0.95f * game.viewport.getWorldWidth() - upgradeButton.getWidth();
        float y = 0.95f * game.viewport.getWorldHeight() - upgradeButton.getHeight();
        upgradeButton.setPosition(x, y);

        upgradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuUpgrade.toggle();
                UpgradeManager.getSingleInstance().getAllUpgrade()[7].setCost(
                        GameLogic.getSingleInstance().getBricks()
                );
            }
        });

        //add Listener to the wall
        wall.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                if (menuUpgrade.isShowed())
                    return;

                GameLogic instance = GameLogic.getSingleInstance();
                double incrementedHealth = instance.getHealth() + instance.getHealingPower();
                instance.setHealth(incrementedHealth);
                instance.setBricks(instance.getBricks() + instance.getBricksPower());
                instance.setScore(instance.getScore() + instance.getBricksPower());
                hammer.show(event.getStageX(), event.getStageY());

                //spawn digit popup here
                PopupLabel temp = popupLabels.remove();
                temp.show(event.getStageX() - hammer.getWidth(), event.getStageY() + hammer.getHeight() / 2f);
                popupLabels.add(temp);
            }
        });

        //Add listener to the pause button
        pause.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                if (menuUpgrade.isShowed())
                    return;

                pause.setVisible(false);
                pauseFond.setVisible(true);
                backButton.setVisible(true);
                GameLogic.getSingleInstance().togglePaused();
                Dynamite.onPause =  GameLogic.getSingleInstance().isPaused();
            }

        });

        dynamite.addListener(dynamite.getListener());

        // health bar
        HealthBar healthBar = new HealthBar(game.ass);
        healthBar.setPosition(stage.getWidth() * 0.05f, stage.getHeight() * 0.86f);
        //

        bricksLabel = new Label("Bricks: " + GameLogic.getSingleInstance().getBricksString(), new Label.LabelStyle(fontOutline, null));
        bricksLabel.setPosition(stage.getWidth() * 0.05f, stage.getHeight() * 0.835f);

        scoreLabel = new Label("Score: " + GameLogic.getSingleInstance().getScoreString(), new Label.LabelStyle(fontOutline, null));
        scoreLabel.setPosition(stage.getWidth() * 0.05f, stage.getHeight() * 0.79f);

        waveLabel = new Label("Score: " + GameLogic.getSingleInstance().getScoreString(), new Label.LabelStyle(fontOutline, null));
        waveLabel.setPosition(0.95f * stage.getWidth() - waveLabel.getWidth(), stage.getHeight() * 0.86f);

        final ImageButton.ImageButtonStyle speedButtonStyle = new ImageButton.ImageButtonStyle();
        speedButtonStyle.up = new TextureRegionDrawable(game.ass.get("ui/texture_button_speedymode.png", Texture.class));
        speedButtonStyle.down = new TextureRegionDrawable(game.ass.get("ui/texture_button_speedymode_down.png", Texture.class));
        ImageButton speedButton = new ImageButton(speedButtonStyle);
        speedButton.setPosition(0.95f * stage.getWidth() - speedButton.getWidth(), 0.85f * stage.getHeight() - speedButton.getHeight());
        speedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                speedyMode = !speedyMode;
            }
        });

        //Add all the things to runescape (add a deadman mode)
        stage.addActor(background);
        stage.addActor(dayNightCycleGroup);
        stage.addActor(this.nuages);
        stage.addActor(wall);
        stage.addActor(dynamiteGroup);
        stage.addActor(ennemiGroup);
        stage.addActor(workerGroup);
        stage.addActor(laserGroup);
        stage.addActor(brixplosionGroup);
        stage.addActor(dollarGroup);
        stage.addActor(nukeExplosionGroup);

        if (GameLogic.getSingleInstance().isCheatMode())
            stage.addActor(speedButton);
        stage.addActor(pause);
        stage.addActor(upgradeButton);
        stage.addActor(bricksLabel);
        stage.addActor(scoreLabel);
        stage.addActor(waveLabel);
        stage.addActor(healthBar);
        stage.addActor(hammerGroup);
        stage.addActor(menuUpgrade);
        stage.addActor(pauseFond);
        stage.addActor(backButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float delta) {
        if (speedyMode)
            delta *= 10;

        double dayNightTime = (GameLogic.getSingleInstance().getDayNightTime() + delta) % GameLogic.DAY_NIGHT_CYCLE_LEN;
        GameLogic.getSingleInstance().setDayNightTime(dayNightTime);

        if (GameLogic.getSingleInstance().isCheatMode())
            GameLogic.getSingleInstance().setBricks(Double.MAX_VALUE);

        if (GameLogic.getSingleInstance().isPaused()) {
            stage.draw();
            return;
        }

        if (GameLogic.getSingleInstance().isTimeSlowed())
            delta /= GameLogic.SLOW_FACTOR;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        bricksLabel.setText("Bricks: " + GameLogic.getSingleInstance().getBricksString());
        scoreLabel.setText("Score: " + GameLogic.getSingleInstance().getScoreString());
        waveLabel.setText("Wave " + (int) MexicanLogic.getSingleInstance().getWaveNumber());
        waveLabel.setSize(waveLabel.getPrefWidth(), waveLabel.getPrefHeight());
        waveLabel.setPosition(0.95f * stage.getWidth() - waveLabel.getWidth(), waveLabel.getY());

        // DYNAMITE EXPLOSION
        MexicanLogic.getSingleInstance().updateDynamite(dynamite);

        // CASH RAIN
        MexicanLogic.getSingleInstance().updateCashRain(dollarGroup);

        // TRUMP HEAD
        MexicanLogic.getSingleInstance().updateTrumpHead(trump, moon);

        // ENNEMI WAVES
        MexicanLogic.getSingleInstance().updateWave(delta);

        stage.act(delta);

        stage.draw();

        if (GameLogic.getSingleInstance().getHealth() <= 0.0f) {
            dispose();
            game.setScreen(new EndScreen(game, nuages, dayNightCycleGroup, ennemiGroup));
        }
    }

    @Override
    public void dispose () {
        stage.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
