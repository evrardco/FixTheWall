package com.fixthewall.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
    private Texture imgWall;
    private Texture imgFond;
    private BitmapFont font;
    private BitmapFont fontUps;
    private Hammer hammer;
    private boolean end;

    public GameScreen(final Game game) {
        batch = new SpriteBatch();
        stage = new Stage();
        imgWall = new Texture("theWall.png");
        imgFond = new Texture("fondWall.png");
        hammer = new Hammer(1);
        this.game = game;

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
        float x = 0.95f*Gdx.graphics.getWidth() - upsButton.getWidth();
        float y = 0.95f*Gdx.graphics.getHeight() - upsButton.getHeight();
        upsButton.setPosition(x, y);

        upsButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                //  dispose();
                //TODO Ecran upgrades
                game.setScreen(new UpgradeScreen(game));
            }
        });
        stage.addActor(upsButton);
        //create a image button
        Button wallButton = new ImageButton(new TextureRegionDrawable(imgWall));
        wallButton.setPosition(0, 300);
        //add Listener to the wall
        wallButton.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                float maxHealth = WallLogic.getSingleInstance().getMaxHealth();
                float incrementedHealth = WallLogic.getSingleInstance().getHealth() + hammer.getPower();
                if(incrementedHealth <= maxHealth){
                    WallLogic.getSingleInstance().setHealth(incrementedHealth);
                }
                else{
                    WallLogic.getSingleInstance().setHealth(maxHealth);
                }
                WallLogic.getSingleInstance().setBricks(WallLogic.getSingleInstance().getBricks() + hammer.getPower());
                hammer.show(event.getStageX(), event.getStageY());
            }
        });
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

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        end = WallLogic.getSingleInstance().getHealth() <= 0.0f;
        batch.draw(imgFond, 0, 0);
        font.draw(batch, "Bricks: "+WallLogic.getSingleInstance().getBricks(), Gdx.graphics.getWidth()/2f,
                (float) (Gdx.graphics.getHeight()*0.9));
        font.draw(batch, "Health: "+(int)WallLogic.getSingleInstance().getHealth()+"/"+(int)WallLogic.getSingleInstance().getMaxHealth(),
                Gdx.graphics.getWidth()/2f, (float) (Gdx.graphics.getHeight()*0.8));
        batch.end();
        if (end)
        {
            game.setScreen(new EndScreen(game));
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose () {
        batch.dispose();
        imgWall.dispose();
        imgFond.dispose();
        font.dispose();
        hammer.dispose();
        stage.dispose();
        game.dispose();
    }
    @Override
    public void show() {

    }



    @Override
    public void resize(int width, int height) {

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
