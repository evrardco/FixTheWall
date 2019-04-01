package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fixthewall.game.actors.Dynamite;
import com.fixthewall.game.actors.Ennemi;
import com.fixthewall.game.actors.EnnemiEchelle;
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
    private Group dollarGroup;
    private Image backgroundNight;
    private Dynamite dynamite;
    private Sun trump;
    private Moon moon;
    //private EnnemiEchelle fUUUUUCKKKKK;

    private LinkedList<PopupLabel> popupLabels;

    public static final transient int DAY_NIGHT_CYCLE_LEN = 20; //10 minutes

    public GameScreen(final Game game) {
        this.game = game;
        stage = new Stage(game.viewport);


        Image backgroundDay = new Image(game.ass.get("fondWall.png", Texture.class));
        backgroundNight = new Image(game.ass.get("fondWall-nuit.png", Texture.class)){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                Color color = getColor();
                super.draw(batch, parentAlpha);
                batch.setColor(color.r, color.g, color.b, parentAlpha);
            }
        };
        backgroundNight.addAction(Actions.alpha(0.0f)); //we begin during the day
        backgroundNight.addAction(Actions.forever(
                Actions.sequence(
                        Actions.alpha(1.0f, DAY_NIGHT_CYCLE_LEN / 2f),
                        Actions.alpha(0.0f, DAY_NIGHT_CYCLE_LEN / 2f)
                )
        ));

        Nuages nuages = new Nuages(game.ass);
        Wall wall = new Wall(game.ass);
        dynamite = new Dynamite(game.ass);
        hammer = new Hammer(game.ass);
        trump = new Sun(game.ass);
        moon = new Moon(game.ass);
        pause = new Image(game.ass.get("imgPause.png", Texture.class));
        pause.setPosition(30, 1810);
        pauseFond = new Image(game.ass.get("imgPauseFond.png", Texture.class));
        pauseFond.addListener(new ClickListener() {
            @Override
            public  void clicked(InputEvent event, float x, float y){
                GameLogic.getSingleInstance().togglePaused();
                Dynamite.onPause = GameLogic.getSingleInstance().isPaused();
                pause.setVisible(true);
                pauseFond.setVisible(false);
            }
        });
        pauseFond.setVisible(false);

        Group ennemiGroup = MexicanLogic.getSingleInstance().getEnnemiGroup();
        Group workerGroup = MexicanLogic.getSingleInstance().getWorkerGroup();
        dollarGroup = new Group();

        Group hammerGroup = new Group();
        hammer = new Hammer(game.ass);
        for(int i=0; i < 2; i++)
            ennemiGroup.addActor(MexicanLogic.getSingleInstance().pool.obtain());




        //Initializing upgrade menu
        final BigMenuTable menuUpgrade = new BigMenuTable(game.ass, "Upgrades");
        menuUpgrade.setVisible(false);

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

        BitmapFont font = game.ass.get("Germania30.ttf"); // font size 12 pixels
        BitmapFont fontUps = game.ass.get("Germania60.ttf");
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = fontUps;
        //Setting up listeners
        Button upsButton = new TextButton("Upgrades", style);
        float x = 0.95f * game.viewport.getWorldWidth() - upsButton.getWidth();
        float y = 0.95f * game.viewport.getWorldHeight() - upsButton.getHeight();
        upsButton.setPosition(x, y);

        upsButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                menuUpgrade.setVisible(!menuUpgrade.isVisible());
            }
        });

        //add Listener to the wall
        wall.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                if (!GameLogic.getSingleInstance().isPaused()) {
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
            }
        });

        //Add listener to the pause button
        pause.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                pause.setVisible(false);
                pauseFond.setVisible(true);
                menuUpgrade.setVisible(false);
                GameLogic.getSingleInstance().togglePaused();
                Dynamite.onPause =  GameLogic.getSingleInstance().isPaused();
            }

        });

        dynamite.addListener(dynamite.getListener());

        bricksLabel = new Label("Bricks: " + GameLogic.getSingleInstance().getBricksString(), new Label.LabelStyle(font, Color.BLACK));
        bricksLabel.setPosition(game.viewport.getWorldWidth() * 0.05f, game.viewport.getWorldHeight() * 0.85f);

        scoreLabel = new Label("Score: " + GameLogic.getSingleInstance().getScoreString(), new Label.LabelStyle(font, Color.BLACK));
        scoreLabel.setPosition(stage.getWidth() * 0.05f, stage.getHeight() * 0.8f);

        // health bar
        HealthBar healthBar = new HealthBar(game.ass);
        healthBar.setPosition(stage.getWidth() * 0.05f, stage.getHeight() * 0.9f);
        //

        //Add all the things to runescape (add a deadman mode)
        stage.addActor(backgroundDay);
        stage.addActor(backgroundNight);
        stage.addActor(nuages);
        stage.addActor(wall);
        stage.addActor(dynamite);
        stage.addActor(ennemiGroup);
        stage.addActor(workerGroup);
        stage.addActor(dollarGroup);
        stage.addActor(pause);
        stage.addActor(trump);
        stage.addActor(moon);
        stage.addActor(upsButton);
        stage.addActor(bricksLabel);
        stage.addActor(scoreLabel);
        stage.addActor(healthBar);
        stage.addActor(hammerGroup);
        stage.addActor(menuUpgrade);
        stage.addActor(pauseFond);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float delta) {

        if (GameLogic.getSingleInstance().isPaused()) {
            stage.draw();
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        bricksLabel.setText("Bricks: " + GameLogic.getSingleInstance().getBricksString());
        scoreLabel.setText("Score: " + GameLogic.getSingleInstance().getScoreString());

        // DYNAMITE EXPLOSION
        MexicanLogic.getSingleInstance().updateDynamite(dynamite);

        // CASH RAIN
        MexicanLogic.getSingleInstance().updateCashRain(dollarGroup, game.ass);

        // TRUMP HEAD
        MexicanLogic.getSingleInstance().updateTrumpHead(trump, moon, delta, DAY_NIGHT_CYCLE_LEN );

        // ENNEMI WAVES
        MexicanLogic.getSingleInstance().updateWave(delta, (backgroundNight.getColor().a <= 0.5f), game.ass);

        stage.act(delta);

        if (GameLogic.getSingleInstance().getHealth() <= 0.0f) {
            dispose();
            game.setScreen(new EndScreen(game));
        }

        stage.draw();
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
