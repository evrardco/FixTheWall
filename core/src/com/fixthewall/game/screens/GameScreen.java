package com.fixthewall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fixthewall.game.actors.Ennemi;
import com.fixthewall.game.actors.Wall;
import com.fixthewall.game.Game;
import com.fixthewall.game.logic.BadGuysLogic;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.actors.Hammer;

public class GameScreen implements Screen {

    private final Game game;
    private Stage stage;
    private Texture textureFond;
    private BitmapFont font;
    private BitmapFont fontUps;
    private Hammer hammer;
    private Wall wall;
    private Ennemi ennemi;
    private Label bricksLabel;
    private Label healthLabel;

    public GameScreen(final Game game) {
        stage = new Stage(game.viewport);
        textureFond = new Texture("fondWall.png");
        Image imgFond = new Image(textureFond);
        stage.addActor(imgFond);
        wall = new Wall();
        hammer = new Hammer(1);
        ennemi = new Ennemi(1);
        this.game = game;

        //Import font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Germania.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter); // font size 12 pixels

        parameter.size = 60;
        fontUps = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = fontUps;
        Button upsButton = new TextButton("Upgrades", style);
        float x = 0.95f * game.viewport.getWorldWidth() - upsButton.getWidth();
        float y = 0.95f * game.viewport.getWorldHeight() - upsButton.getHeight();
        upsButton.setPosition(x, y);

        upsButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new UpgradeScreen(game));
            }
        });
        stage.addActor(upsButton);

        //add Listener to the wall
        wall.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                double maxHealth = GameLogic.getSingleInstance().getMaxHealth();
                double incrementedHealth = GameLogic.getSingleInstance().getHealth() + hammer.getHealPower();
                if(incrementedHealth <= maxHealth){
                    GameLogic.getSingleInstance().setHealth(incrementedHealth);
                }
                else {
                    GameLogic.getSingleInstance().setHealth(maxHealth);
                }
                GameLogic.getSingleInstance().setBricks(GameLogic.getSingleInstance().getBricks() + hammer.getBricksPower());
                hammer.show(event.getStageX(), event.getStageY());
            }
        });
        //Add button to the stage
        stage.addActor(wall);
        stage.addActor(ennemi);
        stage.addActor(hammer);
        //Set the InputProcessor with the stage
        Gdx.input.setInputProcessor(stage);

        bricksLabel = new Label("Bricks: " + (int) GameLogic.getSingleInstance().getBricks(), new Label.LabelStyle(font, Color.BLACK));
        healthLabel = new Label("Health: " + (int) GameLogic.getSingleInstance().getHealth() + "/" + (int) GameLogic.getSingleInstance().getMaxHealth(), new Label.LabelStyle(font, Color.BLACK));
        bricksLabel.setPosition(game.viewport.getWorldWidth() / 2f, game.viewport.getWorldHeight() * 0.9f);
        healthLabel.setPosition(game.viewport.getWorldWidth() / 2f, game.viewport.getWorldHeight() * 0.8f);
        stage.addActor(bricksLabel);
        stage.addActor(healthLabel);
    }

    @Override
    public void render (float delta) {
        //logic update
        BadGuysLogic.getSingleInstance().doDamage(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        bricksLabel.setText("Bricks: " + (int) GameLogic.getSingleInstance().getBricks());
        healthLabel.setText("Health: " + (int) GameLogic.getSingleInstance().getHealth() + "/" + (int) GameLogic.getSingleInstance().getMaxHealth());

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if (GameLogic.getSingleInstance().getHealth() <= 0.0f) {
            dispose();
            game.setScreen(new EndScreen(game));
        }
    }

    @Override
    public void dispose () {
        textureFond.dispose();
        font.dispose();
        fontUps.dispose();
        hammer.dispose();
        wall.dispose();
        ennemi.dispose();
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
