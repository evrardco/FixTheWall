package com.fixthewall.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fixthewall.game.Game;

public class GameScreen implements Screen {
    private final Game game;
    private Stage stage;
    private SpriteBatch batch;
    private Texture imgWall;
    private Texture imgFond;
    private BitmapFont font;
    private int healthIncrement;
    private int bricksIncrement;


    public GameScreen(final Game game) {
        batch = new SpriteBatch();
        stage = new Stage();
        imgWall = new Texture("theWall.png");
        imgFond = new Texture("fondWall.png");
        this.game = game;
        //incrementation de base.
        healthIncrement=1;
        bricksIncrement=1;

        //Import font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Germania.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        //create a image button
        Button wallButton = new ImageButton(new TextureRegionDrawable(imgWall));
        wallButton.setPosition(0, 300);
        //add Listener to the wall
        wallButton.addListener( new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                int maxHealth = game.wallLogic.getMaxHealth();
                int incrementedHealth = game.wallLogic.getHealth()+healthIncrement;
                if(incrementedHealth <= maxHealth){
                    game.wallLogic.setHealth(incrementedHealth);
                }
                else{
                    game.wallLogic.setHealth(maxHealth);
                }
                game.wallLogic.setBricks(game.wallLogic.getBricks()+bricksIncrement);
            }
        });
        //Add button to the stage
        stage.addActor(wallButton);
        //Set the InputProcessor with the stage
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgFond, 0, 0);
        batch.draw(imgWall, 0, 300);
        font.draw(batch, "Bricks: "+game.wallLogic.getBricks(), Gdx.graphics.getWidth()/2f,
                (float) (Gdx.graphics.getHeight()*0.9));
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        imgWall.dispose();
        imgFond.dispose();
        font.dispose();
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
