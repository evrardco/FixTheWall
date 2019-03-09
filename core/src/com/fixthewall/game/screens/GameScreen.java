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
import com.fixthewall.game.actors.Ennemi;
import com.fixthewall.game.actors.Nuages;
import com.fixthewall.game.actors.Wall;
import com.fixthewall.game.Game;
import com.fixthewall.game.logic.BadGuysLogic;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.actors.Hammer;

public class GameScreen implements Screen {

    private final Game game;
    private Stage stage;
    private Hammer hammer;
    private Label bricksLabel;
    private Label healthLabel;
    private Group ennemiGroup;
    public static GameScreen gameScreen;

    public GameScreen(final Game game) {
        stage = new Stage(game.viewport);
        Texture textureFond = game.ass.get("fondWall.png");

        Image imgFond = new Image(textureFond);
        Nuages nuages = new Nuages(game.ass);
        Wall wall = new Wall(game.ass);
        ennemiGroup = new Group();
        hammer = new Hammer(game.ass);
        Ennemi ennemi = new Ennemi(1, game.ass);

        this.game = game;

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
                game.setScreen(new UpgradeScreen(game));
            }
        });

        //add Listener to the wall
        wall.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                GameLogic instance = GameLogic.getSingleInstance();
                double maxHealth = instance.getMaxHealth();
                double incrementedHealth = instance.getHealth() + instance.getHealingPower();
                if(incrementedHealth <= maxHealth){
                    instance.setHealth(incrementedHealth);
                }
                else {
                    instance.setHealth(maxHealth);
                }
                instance.setBricks(instance.getBricks() + instance.getBricksPower());
                hammer.show(event.getStageX(), event.getStageY());
            }
        });

        bricksLabel = new Label("Bricks: " + (int) GameLogic.getSingleInstance().getBricks(), new Label.LabelStyle(font, Color.BLACK));
        healthLabel = new Label("Health: " + (int) GameLogic.getSingleInstance().getHealth() + "/" + (int) GameLogic.getSingleInstance().getMaxHealth(), new Label.LabelStyle(font, Color.BLACK));
        bricksLabel.setPosition(game.viewport.getWorldWidth() / 2f, game.viewport.getWorldHeight() * 0.9f);
        healthLabel.setPosition(game.viewport.getWorldWidth() / 2f, game.viewport.getWorldHeight() * 0.8f);

        //Add all the things to runescape
        stage.addActor(imgFond);
        stage.addActor(nuages);
        stage.addActor(wall);
        stage.addActor(ennemiGroup);
        ennemiGroup.addActor(ennemi);
        stage.addActor(upsButton);
        stage.addActor(bricksLabel);
        stage.addActor(healthLabel);
        stage.addActor(hammer);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float delta) {
        //logic update
        BadGuysLogic.getSingleInstance().doDamage(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        bricksLabel.setText("Bricks: " + (int) GameLogic.getSingleInstance().getBricks());
        healthLabel.setText("Health: " + (int) GameLogic.getSingleInstance().getHealth() + "/" + (int) GameLogic.getSingleInstance().getMaxHealth());
        //ennemiGroup.addActor(new Ennemi(0, game.ass));

        stage.act(delta);
        stage.draw();

        if (GameLogic.getSingleInstance().getHealth() <= 0.0f) {
            dispose();
            gameScreen = null;
            game.setScreen(new EndScreen(game));
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
