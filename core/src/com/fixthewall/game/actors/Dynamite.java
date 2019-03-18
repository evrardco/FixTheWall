package com.fixthewall.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fixthewall.game.logic.GameLogic;

public class Dynamite extends Actor{

    private double degats;
    private boolean visible;
    private Animation<TextureRegion> DynamiteAnimation;
    // Variable for tracking elapsed time for the animation
    private float elapsedTime;
    private float Time;
    private Texture texture;
    private int level;

    public Dynamite (AssetManager ass) {
        texture = ass.get("Frames/SheetFrameDynamite.png");
        degats = 25;
        level = 1;
        //set size actor
        setWidth(texture.getWidth()/3f);
        setHeight(texture.getHeight());
        visible = false;
        setTouchable(Touchable.disabled);

        //Set animation
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth()/3,
                texture.getHeight());
        TextureRegion[] DynamiteFrames = new TextureRegion[3];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                DynamiteFrames[index++] = tmp[i][j];
            }
        }
        DynamiteAnimation = new Animation<TextureRegion>(0.1f, DynamiteFrames);
        //
        elapsedTime =0f;
        Time=0f;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (visible)
        {
            Time = Time + delta;
        }
        if (Time > 10f)
        {
            GameLogic.getSingleInstance().reduceHealth(degats);
            Time = 0f;
            visible = false;
            setTouchable(Touchable.disabled);
        }
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (getRandom(300) == 10 && !visible)
        {
            visible = true;
            setTouchable(Touchable.enabled);
            this.setPosition(getRandom(1021), 300+getRandom(461));
        }
        if(visible) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            batch.draw(DynamiteAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
        }
    }

    public ClickListener getListener(){
        return  new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                visible = false;
                setTouchable(Touchable.disabled);
                Time = 0;
            }
        };

    }

    public void setLevel(int level){
        this.level = level;
        this.degats = 25*level*level;
    }

    /*
     * Retourne un entier aléatoire entre 0 et n-1
     */
    private static int getRandom(int n){
        return (int)(Math.random()*n);
    }

}
