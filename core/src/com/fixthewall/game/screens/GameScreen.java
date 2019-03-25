package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.SnapshotArray;
import com.fixthewall.game.actors.Dynamite;
import com.fixthewall.game.actors.Ennemi;
import com.fixthewall.game.actors.BigMenuTable;
import com.fixthewall.game.actors.HealthBar;
import com.fixthewall.game.actors.Nuages;
import com.fixthewall.game.actors.PopupLabel;
import com.fixthewall.game.actors.UpgradeButton;
import com.fixthewall.game.actors.Wall;
import com.fixthewall.game.Game;
import com.fixthewall.game.actors.anim.Brixplosion;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.actors.Hammer;
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
    private Group ennemiGroup;
    private Group animGroup;
    private Image imgFond;
    private Dynamite dyn;
    private double totalTime;
    private boolean isNight;
    private double dailyTime;
    private Texture textureFond;
    private Texture textureFondNight;
    private int wave;
    private int ennemiToRemove;
    private SnapshotArray<Actor> actorsArray;
    private Actor[] groupToArray;

    private LinkedList<PopupLabel> popupLabels;

    public static GameScreen gameScreen;

    public GameScreen(final Game game) {
        this.game = game;

        stage = new Stage(game.viewport);
        textureFond = game.ass.get("fondWall.png");
        textureFondNight = game.ass.get("fondWall-nuit.png");

        imgFond = new Image(textureFond);
        Nuages nuages = new Nuages(game.ass);
        Wall wall = new Wall(game.ass);
        dyn = new Dynamite(game.ass);
        hammer = new Hammer(game.ass);
        pause = new Image(game.ass.get("imgPause.png", Texture.class));
        pause.setPosition(30, 1810);
        pauseFond = new Image(game.ass.get("imgPauseFond.png", Texture.class));
        pauseFond.setVisible(false);
        wave = 0;
        isNight = false;
        dailyTime = 0;
        ennemiToRemove = 0;
        actorsArray = null;
        groupToArray = null;

        ennemiGroup = new Group();

        Group hammerGroup = new Group();
        animGroup = new Group();
        hammer = new Hammer(game.ass);

        Ennemi ennemy = new Ennemi(1, game.ass);
        ennemy.addListener(new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                float betterX = event.getStageX();
                float betterY = event.getStageY();
                Actor actor = event.getListenerActor();
                Brixplosion explosion = new Brixplosion(15, game.ass, betterX, betterY);
                explosion.setPosition(betterX, betterY);
                animGroup.addActor(explosion);
                actor.remove();

                Gdx.app.log("GameScreen", "Ennemy touched");
            }

        });
        ennemiGroup.addActor(ennemy);


        //Initializing upgrade menu
        final BigMenuTable menuUpgrade = new BigMenuTable(game.ass, "Upgrades");
        menuUpgrade.setVisible(false);

        AbstractUpgrade[] upArray = UpgradeManager.getSingleInstance().getAllUpgrade();
        for(int i=0; i < upArray.length; i++) {
            menuUpgrade.addEntry(new UpgradeButton(game.ass, upArray[i]));
        }

        totalTime = 0;
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
                    double maxHealth = instance.getMaxHealth();
                    double incrementedHealth = instance.getHealth() + instance.getHealingPower();
                    if (incrementedHealth <= maxHealth) {
                        instance.setHealth(incrementedHealth);
                    } else {
                        instance.setHealth(maxHealth);
                    }
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

        dyn.addListener(dyn.getListener());

        bricksLabel = new Label("Bricks: " + GameLogic.getSingleInstance().getBricksString(), new Label.LabelStyle(font, Color.BLACK));
        bricksLabel.setPosition(game.viewport.getWorldWidth() * 0.05f, game.viewport.getWorldHeight() * 0.85f);

        scoreLabel = new Label("Score: " + GameLogic.getSingleInstance().getScoreString(), new Label.LabelStyle(font, Color.BLACK));
        scoreLabel.setPosition(stage.getWidth() * 0.05f, stage.getHeight() * 0.8f);

        // health bar
        HealthBar healthBar = new HealthBar(game.ass);
        healthBar.setPosition(stage.getWidth() * 0.05f, stage.getHeight() * 0.9f);
        //

        //Add all the things to runescape (add a deadman mode)
        stage.addActor(imgFond);
        stage.addActor(nuages);
        stage.addActor(wall);
        stage.addActor(dyn);
        stage.addActor(ennemiGroup);
        ennemiGroup.addActor(ennemy); //ou sinon le premier ennemy n'est pas ajouté au stage
        stage.addActor(pause);
        stage.addActor(animGroup);
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

        //pause code

        if (!GameLogic.getSingleInstance().isPaused()) {
            //logic update
            totalTime = totalTime + delta;
            dailyTime = dailyTime + delta;
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            bricksLabel.setText("Bricks: " + GameLogic.getSingleInstance().getBricksString());
            scoreLabel.setText("Score: " + GameLogic.getSingleInstance().getScoreString());

            actorsArray = ennemiGroup.getChildren();
            groupToArray = actorsArray.begin();
            ennemiToRemove = GameLogic.getSingleInstance().getEnnemiRemoval();
            for (int i = 0; i< ennemiToRemove; i++)
            {
                Gdx.app.log("Upgrade 3", "Passage bloucle");
                 if (actorsArray.size > 0)
                 {
                     Gdx.app.log("Upgrade 3", "Passage bloucle2");
                      groupToArray[i].remove();
                 }
            }
            GameLogic.getSingleInstance().setEnnemiRemoval(0);
            if ((totalTime > 45 && !isNight) || (totalTime > 25 && isNight))
            {
                totalTime = 0f;
                wave++;
                for (int i = 0; i < 1+2*wave; i++)
                {
                    final Actor ennemy = new Ennemi(wave, game.ass);
                    ennemy.addListener(new ClickListener(){
                        @Override
                        public  void clicked(InputEvent event, float x, float y){
                            animGroup.addActor(new Brixplosion(15, game.ass, x, y));
                            ennemy.remove();
                        }

                    });

                    ennemiGroup.addActor(ennemy);
                }
                dyn.setLevel(wave);
            }

            if (dailyTime > 300f)
            {
                dailyTime = 0f;
                isNight = !isNight;
                if (isNight)
                {
                    imgFond.setDrawable(new TextureRegionDrawable(new TextureRegion(textureFondNight)));
                }
                else {
                    imgFond.setDrawable(new TextureRegionDrawable(new TextureRegion(textureFond)));
                }
            }

            stage.act(delta);

            if (GameLogic.getSingleInstance().getHealth() <= 0.0f) {
                dispose();
                gameScreen = null;
                game.setScreen(new EndScreen(game));
            }
        }
        else if (Gdx.input.justTouched()) {
            GameLogic.getSingleInstance().togglePaused();
            Dynamite.onPause = GameLogic.getSingleInstance().isPaused();
            pause.setVisible(true);
            pauseFond.setVisible(false);
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
