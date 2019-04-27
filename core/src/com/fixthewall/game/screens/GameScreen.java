package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.fixthewall.game.Perziztancinator;
import com.fixthewall.game.actors.Dynamite;
import com.fixthewall.game.actors.EnnemiBaleze;
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
    private Group dayNightCycleGroup;
    private Group dayNightBackground;
    private Sun trump;
    private Moon moon;
    private Nuages nuages;

    private LinkedList<PopupLabel> popupLabels;

    public static final transient int DAY_NIGHT_CYCLE_LEN = 300; //10 minutes

    public GameScreen(final Game game, Nuages nuages) {
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
        backgroundNight.setColor(
                backgroundNight.getColor().r,
                backgroundNight.getColor().g,
                backgroundNight.getColor().b,
                0.0f
        );
        moon = new Moon(game.ass);
        trump = new Sun(game.ass);

        setupNightCycle();



        backgroundNight.getActions();
        if (nuages == null)
            this.nuages = new Nuages(game.ass);
        else
            this.nuages = nuages;

        Wall wall = new Wall(game.ass);
        dynamite = new Dynamite(game.ass);
        Group dynamiteGroup = new Group();
        dynamiteGroup.addActor(dynamite);
        hammer = new Hammer(game.ass);
        dayNightCycleGroup = MexicanLogic.getSingleInstance().getDayNightCycleGroup();
        dayNightBackground = new Group();
        dayNightBackground.addActor(backgroundDay);
        dayNightBackground.addActor(backgroundNight);
        dayNightCycleGroup.addActor(moon);
        dayNightCycleGroup.addActor(trump);

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
            }
        });
        pauseFond.setVisible(false);

        Group ennemiGroup = MexicanLogic.getSingleInstance().getEnnemiGroup();
        Group workerGroup = MexicanLogic.getSingleInstance().getWorkerGroup();
        dollarGroup = new Group();
        dollarGroup.getChildren().ensureCapacity(256);
        Group hammerGroup = new Group();
        hammer = new Hammer(game.ass);
        for(int i=0; i < 2; i++)
            ennemiGroup.addActor(MexicanLogic.getSingleInstance().pool.obtain());


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
        bricksLabel.setPosition(game.viewport.getWorldWidth() * 0.05f, game.viewport.getWorldHeight() * 0.81f);

        scoreLabel = new Label("Score: " + GameLogic.getSingleInstance().getScoreString(), new Label.LabelStyle(fontOutline, null));
        scoreLabel.setPosition(stage.getWidth() * 0.05f, stage.getHeight() * 0.76f);


        //Add all the things to runescape (add a deadman mode)
        stage.addActor(dayNightBackground);
        stage.addActor(dayNightCycleGroup);
        stage.addActor(this.nuages);
        stage.addActor(wall);
        stage.addActor(dynamiteGroup);
        stage.addActor(ennemiGroup);
        stage.addActor(workerGroup);
        stage.addActor(dollarGroup);
        stage.addActor(pause);

        stage.addActor(upgradeButton);
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
        delta*=11.025;
        MexicanLogic.getSingleInstance().setDayNightCycle(trump, moon, backgroundNight);//persistance
//        delta = 10*delta;

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


        // DYNAMITE EXPLOSION
        MexicanLogic.getSingleInstance().updateDynamite(dynamite);

        // CASH RAIN
        MexicanLogic.getSingleInstance().updateCashRain(dollarGroup, game.ass);

        // TRUMP HEAD
        MexicanLogic.getSingleInstance().updateTrumpHead(trump, moon, delta, DAY_NIGHT_CYCLE_LEN );

        // ENNEMI WAVES
        MexicanLogic.getSingleInstance().updateWave(delta, (backgroundNight.getColor().a <= 0.5f), game.ass);

        stage.act(delta);

        stage.draw();

        if (GameLogic.getSingleInstance().getHealth() <= 0.0f) {
            dispose();
            game.setScreen(new EndScreen(game, nuages, dayNightCycleGroup, dayNightBackground));
        }
    }

    private void setupNightCycle(){
        float baseAlpha = 0.0f;
        float cycleOffset = 0.0f;
        float goalAlpha = 1.0f;
        if(Perziztancinator.getSingleInstance().isGameLoaded()){
            Vector2 sunPos = MexicanLogic.getSingleInstance().getSunPos();
            Vector2 moonPos = MexicanLogic.getSingleInstance().getMoonPos();
            baseAlpha = MexicanLogic.getSingleInstance().getNightAlpha();

            cycleOffset = GameLogic.getSingleInstance().getTotalTime() % (DAY_NIGHT_CYCLE_LEN);
            if(sunPos != null)moon.setPosition(moonPos.x, moonPos.y);
            if(sunPos != null)trump.setPosition(sunPos.x, sunPos.y);
            if(cycleOffset > DAY_NIGHT_CYCLE_LEN/2)
                goalAlpha = 0.0f;


        }
        backgroundNight.addAction(Actions.sequence(
                Actions.alpha(baseAlpha),
                Actions.alpha(goalAlpha, Math.abs(cycleOffset - DAY_NIGHT_CYCLE_LEN/2)),
                Actions.alpha(0.0f), //we begin during the day
                Actions.forever(
                        Actions.sequence(
                                Actions.fadeIn(DAY_NIGHT_CYCLE_LEN / 2f),
                                Actions.fadeOut(DAY_NIGHT_CYCLE_LEN / 2f)
                        )
                )
        ));
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
