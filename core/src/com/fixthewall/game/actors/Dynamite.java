package com.fixthewall.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fixthewall.game.logic.GameLogic;

public class Dynamite extends Actor{

    public static boolean onPause;
    private double degats;
    private boolean visible;
    private Animation<TextureRegion> DynamiteAnimation;
    // Variable for tracking elapsed time for the animation
    private float elapsedTime;
    private float Time;
    private Texture texture;
    private boolean isFalling;
    private boolean isExploding;
    private int level;
    private Rectangle bounds;

    public Dynamite (AssetManager ass) {
        texture = ass.get("Frames/SheetFrameDynamite.png");
        degats = 25;
        level = 1;
        onPause = false;
        isFalling = false;
        isExploding = false;
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
        bounds=new Rectangle((this.getX()+100), this.getY()+100, (this.getWidth()+100), this.getHeight());

    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (!isFalling) {

            if (visible) {
                Time = Time + delta;
            }
            if (Time > 10f) {
                GameLogic.getSingleInstance().reduceHealth(degats);
                Time = 0f;
                visible = false;
                setTouchable(Touchable.disabled);
            }
        }
        else {
            this.setY(this.getY()-((this.getY()*delta)/2));
            if (this.getY() < 300)
            {
                this.explode();
            }
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
                if (!onPause) {
                    isFalling = true;
                }
            }
        };

    }

    public void setLevel(int level){
        this.level = level;
        this.degats = 25*level*level;
    }

    public Rectangle getBounds()
    {
        bounds=new Rectangle((this.getX()+100), this.getY()+100, (this.getWidth()+100), this.getHeight());
        return bounds;

    }

    public void explode()
    {
        isFalling = false;
        isExploding = true;
        visible = false;
        setTouchable(Touchable.disabled);
        Time = 0;
    }

    public void setExploding(boolean val)
    {
        isExploding = false;
    }
    public boolean getExploding()
    {
        return isExploding;
    }

    /*
     * Retourne un entier aléatoire entre 0 et n-1
     */
    private static int getRandom(int n){
        return (int)(Math.random()*n);
    }

}
