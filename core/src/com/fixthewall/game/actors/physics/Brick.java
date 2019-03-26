package com.fixthewall.game.actors.physics;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fixthewall.game.Game;

public class Brick extends Actor {
    private float alpha;
    private float y;
    private float x;
    private float velX;
    private float velY;
    private float angVel;
    private float ttl;
    private float groundLevel;
    private Sprite sprite;
    private boolean isPulledBygravity;
    private boolean onGround;



    public Brick(float x, float y, float velX, float velY, float angVel, float ttl, boolean gravity, AssetManager ass){
        this.x = x;
        this.y = y;
        this.groundLevel = this.y - 100f; //arbitrary should be changed
        this.velX = velX;
        this.velY = velY;
        this.angVel = angVel;
        this.ttl = ttl;
        this.isPulledBygravity = gravity;
        this.sprite = new Sprite((Texture)ass.get("anim/brick.png"));
        this.sprite.setPosition(x, y);
        onGround = false;
        this.alpha = 1.0f;


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch, alpha);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setPosition(sprite.getX(), sprite.getY());
        //  Gdx.app.log("Brick", "velX = "+velX);
        sprite.setPosition(getX() + velX * delta, getY() + velY * delta);
        sprite.rotate(angVel * delta);

        //Gdx.app.log("Brick", ""+onGround+"\n\n\n");
        if(!onGround && sprite.getY() <= groundLevel){
            velY = 0;
            sprite.setRotation(0f);
            angVel = 0.0f;
            onGround = true;
        }if(onGround){
            sprite.setY(groundLevel);
            velX += Constants.SLOWING_FACTOR * -1.0f * Math.signum(velX) * delta;
        } else if(isPulledBygravity){
            velY += Constants.GRAVITY * delta;
        }
        //here we do the fading stuff
        ttl = Math.max(ttl-delta, 0.0f);
        Gdx.app.log("Brick", "ttl: "+ttl);
        if(ttl <= 0.0f){
            this.alpha -= delta;
            Gdx.app.log("Brick", "alpha: "+alpha);

            if(alpha < 0.0f){
                this.remove();
            }
        }
    }

    public void setVelInDir(float degrees, float speedAbs){
        degrees = (float)Math.toRadians(degrees);
        velX = (float)Math.cos(degrees) * speedAbs;
        velY = (float)Math.sin(degrees) * speedAbs;
    }

}
