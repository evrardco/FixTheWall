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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fixthewall.game.Game;
import com.fixthewall.game.logic.GameLogic;

public class Dynamite extends Actor{

    private float degats;
    private float targetX;
    private float targetY;
    private int level;
    private boolean visible;
    private boolean fromLeft;
    private Animation<TextureRegion> ennemiAnimation;
    private Animation<TextureRegion> ennemiAnimationHit;
    // Variable for tracking elapsed time for the animation
    private float Time;
    private float elapsedTimeHit;
    private Texture texture;
    public Dynamite (AssetManager ass) {

        texture = ass.get("nuke.png");
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        setPosition(4000, 4000);
        visible = false;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (visible)
        {
            Time = Time + delta;
        }
        if (Time > 10)
        {
            GameLogic.getSingleInstance().reduceHealth();
            setPosition(4000, 4000);
            Time = 0;
            visible = false;

        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (getRandom(300) == 10 && !visible)
        {
            visible = true;
            this.setPosition(getRandom(700), 300+getRandom(300));
        }
        batch.draw(texture, getX(), getY());
    }

    public ClickListener getListener(){
        return  new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){

                setPosition(3000, 3000);
                visible = false;


            }
        };

    }


    /*
     * Retourne un entier aléatoire entre 0 et n-1
     */
    private static int getRandom(int n){
        return (int)(Math.random()*n);
    }

}
