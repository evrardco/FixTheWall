package com.fixthewall.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.utils.Timer;
import com.fixthewall.game.Game;
import com.fixthewall.logic.BadGuysLogic;
import com.fixthewall.logic.WallLogic;
import com.fixthewall.logic.WallLogic;

public class GameScreen implements Screen {
    private final Game game;
    private Stage stage;
    private SpriteBatch batch;
    private Texture imgWall;
    private Texture imgFond;
    private BitmapFont font;
    private BitmapFont fontUps;
    private Music song1;
    private Music song2;
    private Music song3;
    private Music song4;
    private Music song5;
    private Music song6;
    private int healthIncrement;
    private int bricksIncrement;
    private boolean end;


    public GameScreen(final Game game) {
        batch = new SpriteBatch();
        stage = new Stage();
        imgWall = new Texture("theWall.png");
        imgFond = new Texture("fondWall.png");
        this.game = game;
        //music
        song1 = Gdx.audio.newMusic(Gdx.files.internal("music/song1.mp3"));
        song2 = Gdx.audio.newMusic(Gdx.files.internal("music/song2.mp3"));
        song3 = Gdx.audio.newMusic(Gdx.files.internal("music/song3.mp3"));
        song4 = Gdx.audio.newMusic(Gdx.files.internal("music/song4.mp3"));
        song5 = Gdx.audio.newMusic(Gdx.files.internal("music/song5.mp3"));
        song6 = Gdx.audio.newMusic(Gdx.files.internal("music/song6.mp3"));
        musicRunning();
        //incrementation de base.
        healthIncrement=1;
        bricksIncrement=1;
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
                float incrementedHealth = WallLogic.getSingleInstance().getHealth()+healthIncrement;
                if(incrementedHealth <= maxHealth){
                    WallLogic.getSingleInstance().setHealth(incrementedHealth);
                }
                else{
                    WallLogic.getSingleInstance().setHealth(maxHealth);
                }
                WallLogic.getSingleInstance().setBricks(WallLogic.getSingleInstance().getBricks()+bricksIncrement);
            }
        });
        //Add button to the stage
        stage.addActor(wallButton);
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
        batch.draw(imgWall, 0, 300);
        font.draw(batch, "Bricks: "+WallLogic.getSingleInstance().getBricks(), Gdx.graphics.getWidth()/2f,
                (float) (Gdx.graphics.getHeight()*0.9));
        font.draw(batch, "Health: "+(int)WallLogic.getSingleInstance().getHealth()+"/"+(int)WallLogic.getSingleInstance().getMaxHealth(),
                Gdx.graphics.getWidth()/2f, (float) (Gdx.graphics.getHeight()*0.8));
        batch.end();
        if (end)
        {
            dispose();
            game.setScreen(new EndScreen(game));
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose () {
        batch.dispose();
        imgWall.dispose();
        imgFond.dispose();
        font.dispose();
        stage.dispose();
        game.dispose();
        song1.dispose();
        song2.dispose();
        song3.dispose();
        song4.dispose();
        song5.dispose();
        song6.dispose();
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

    private void musicRunning(){
        //Lancement
        song1.play();
        //Gestion des transitions
        song1.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                song2.play();
            }
        });
        song2.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                song3.play();
            }
        });
        song3.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                song4.play();
            }
        });
        song4.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                song5.play();
            }
        });
        song5.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                song6.play();
            }
        });
        song6.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                song1.play();
            }
        });
        //Gestion des fondus audio
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(song1.isPlaying()){
                    if(song1.getPosition() >= 215){
                        song1.setVolume(song1.getVolume() - 0.1f);
                    }
                }
                if(song2.isPlaying()){
                    if(song2.getPosition() >= 215){
                        song2.setVolume(song2.getVolume() - 0.1f);
                    }
                }
                if(song3.isPlaying()){
                    if(song3.getPosition() >= 215){
                        song3.setVolume(song3.getVolume() - 0.1f);
                    }
                }
                if(song4.isPlaying()){
                    if(song4.getPosition() >= 215){
                        song4.setVolume(song4.getVolume() - 0.1f);
                    }
                }
                if(song5.isPlaying()){
                    if(song5.getPosition() >= 215){
                        song5.setVolume(song5.getVolume() - 0.1f);
                    }
                }
                if(song6.isPlaying()){
                    if(song6.getPosition() >= 215){
                        song6.setVolume(song6.getVolume() - 0.1f);
                    }
                }
                if(!song1.isPlaying()){song1.setVolume(1);}
                if(!song2.isPlaying()){song2.setVolume(1);}
                if(!song3.isPlaying()){song3.setVolume(1);}
                if(!song4.isPlaying()){song4.setVolume(1);}
                if(!song5.isPlaying()){song5.setVolume(1);}
                if(!song6.isPlaying()){song6.setVolume(1);}
            }
        }, 10,0.4f,20);

    }


}
