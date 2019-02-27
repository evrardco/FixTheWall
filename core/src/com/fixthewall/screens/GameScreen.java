package com.fixthewall.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fixthewall.game.Game;
import com.fixthewall.logic.BadGuysLogic;
import com.fixthewall.logic.WallLogic;
import com.fixthewall.actors.Hammer;

public class GameScreen implements Screen {
    private final Game game;
    private Stage stage;
    private SpriteBatch batch;
    private TextureRegion imgWall;
    private TextureRegion imgWall2;
    private Texture textureFond;
    private BitmapFont font;
    private BitmapFont fontUps;
    private Hammer hammer;
    private boolean end;
    private TextureRegionDrawable txtreg;
    private int wallBefore;
    private int wallAfter;

    public GameScreen(final Game game) {
        batch = new SpriteBatch();
        stage = new Stage(game.viewport);
        imgWall = new TextureRegion(new Texture("theWall.png"));
        imgWall2 = new TextureRegion(new Texture("theWallHalf.png"));
        textureFond = new Texture("fondWall.png");
        Image imgFond = new Image(textureFond);
        stage.addActor(imgFond);
        hammer = new Hammer(1);
        this.game = game;
        txtreg =new TextureRegionDrawable(imgWall);


        end = false;

        //Import font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Germania.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
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
                //TODO Ecran upgrades
                game.setScreen(new UpgradeScreen(game));
            }
        });
        stage.addActor(upsButton);
        //create a image button
        Button wallButton = new ImageButton(txtreg);
        wallButton.setPosition(0, 300);
        //add Listener to the wall
        wallButton.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                float maxHealth = WallLogic.getSingleInstance().getMaxHealth();
                float incrementedHealth = WallLogic.getSingleInstance().getHealth() + hammer.getHealPower();
                if(incrementedHealth <= maxHealth){
                    WallLogic.getSingleInstance().setHealth(incrementedHealth);
                }
                else{
                    WallLogic.getSingleInstance().setHealth(maxHealth);
                }
                WallLogic.getSingleInstance().setBricks(WallLogic.getSingleInstance().getBricks() + hammer.getBricksPower());
                hammer.show(event.getStageX(), event.getStageY());
            }
        });
        wallBefore = 0;
        wallAfter = 0;
        //Add button to the stage
        stage.addActor(wallButton);
        stage.addActor(hammer);
        //Set the InputProcessor with the stage
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render (float delta) {
        //logic update
        BadGuysLogic.getSingleInstance().doDamage();
        if (WallLogic.getSingleInstance().getHealth() < 50.0) {
            wallAfter = 0;
            txtreg.setRegion(imgWall2);
        }
        else {
                wallAfter = 1;//Let's buuild this wall
        }
        if (wallAfter != wallBefore)
        {
            switch (wallAfter) {
                case 0: txtreg.setRegion(imgWall2);
                break;
                case 1: txtreg.setRegion(imgWall);
                break;
            }
        }
        wallBefore = wallAfter;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        batch.begin();
        end = WallLogic.getSingleInstance().getHealth() <= 0.0f;
        font.draw(batch, "Bricks: " + WallLogic.getSingleInstance().getBricks(), game.viewport.getWorldWidth() / 2f,
                game.viewport.getWorldWidth() * 0.9f);
        font.draw(batch, "Health: " + (int) WallLogic.getSingleInstance().getHealth() + "/" + (int) WallLogic.getSingleInstance().getMaxHealth(),
                game.viewport.getWorldWidth() / 2f, game.viewport.getWorldWidth() * 0.8f);
        batch.end();

        if (end) {
            game.setScreen(new EndScreen(game));
        }
    }

    @Override
    public void dispose () {
        batch.dispose();
        textureFond.dispose();
        font.dispose();
        fontUps.dispose();
        hammer.dispose();
        stage.dispose();
        game.dispose();
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
