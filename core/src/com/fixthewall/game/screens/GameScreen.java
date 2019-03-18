package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.fixthewall.game.actors.Dynamite;
import com.fixthewall.game.actors.Ennemi;
import com.fixthewall.game.actors.MenuTable;
import com.fixthewall.game.actors.HealthBar;
import com.fixthewall.game.actors.Nuages;
import com.fixthewall.game.actors.PauseButton;
import com.fixthewall.game.actors.PopupLabel;
import com.fixthewall.game.actors.UpgradeButton;
import com.fixthewall.game.actors.Wall;
import com.fixthewall.game.Game;
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
    private PauseButton pause;
    private Label bricksLabel;
    private Label scoreLabel;
    private Boolean onPause;
    private Group ennemiGroup;
    private Dynamite dyn;
    private double totalTime;
    private int wave;

    private LinkedList<PopupLabel> popupLabels;

    public static GameScreen gameScreen;

    public GameScreen(final Game game) {
        this.game = game;

        stage = new Stage(game.viewport);
        Texture textureFond = game.ass.get("fondWall.png");

        Image imgFond = new Image(textureFond);
        Nuages nuages = new Nuages(game.ass);
        Wall wall = new Wall(game.ass);
        dyn = new Dynamite(game.ass);
        hammer = new Hammer(game.ass);
        pause = new PauseButton(game.ass);
        wave = 0;

        ennemiGroup = new Group();
        Ennemi ennemi = new Ennemi(1, game.ass);
        ennemiGroup.addActor(ennemi);
        onPause = false;

        Group hammerGroup = new Group();
        hammer = new Hammer(game.ass);

        //Initializing upgrade menu
        final MenuTable menuUpgrade = new MenuTable(game.ass, "Upgrades");
        menuUpgrade.setVisible(false);

        AbstractUpgrade[] upArray = UpgradeManager.getSingleInstance().getAllUpgrade();
        for(int i=0; i < upArray.length; i++) {
            menuUpgrade.addEntry("", new UpgradeButton(game.ass, upArray[i]));
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
                if (!onPause) {
                    menuUpgrade.setVisible(!menuUpgrade.isVisible());
                }
            }
        });

        //add Listener to the wall
        wall.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                if (!onPause) {
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
        pause.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                onPause = !onPause;
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
        stage.addActor(ennemi);
        stage.addActor(pause);
        stage.addActor(ennemiGroup);
        stage.addActor(upsButton);
        stage.addActor(bricksLabel);
        stage.addActor(scoreLabel);
        stage.addActor(healthBar);
        stage.addActor(hammerGroup);
        stage.addActor(menuUpgrade);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float delta) {
        if (!onPause) {
            //logic update
            totalTime = totalTime + delta;
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            bricksLabel.setText("Bricks: " + GameLogic.getSingleInstance().getBricksString());
            scoreLabel.setText("Score: " + GameLogic.getSingleInstance().getScoreString());


            if (totalTime/90 > 1)
            {
                totalTime = 0;
                wave++;
                for (int i = 0; i < 10+2*wave; i++)
                {
                    ennemiGroup.addActor(new Ennemi(wave, game.ass));
                    dyn.setLevel(wave);
                }
            }

            stage.act(delta);
            stage.draw();

            if (GameLogic.getSingleInstance().getHealth() <= 0.0f) {
                dispose();
                gameScreen = null;
                game.setScreen(new EndScreen(game));
            }
        }
        else {

            //add pause design at center ?

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
