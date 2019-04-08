package com.fixthewall.game.actors.physics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fixthewall.game.actors.anim.Brixplosion;

public class BaseParticle extends Actor {
    protected float alpha;
    protected float y;
    protected float x;
    protected float velX;
    protected float velY;
    protected float angVel;
    protected float ttl;
    protected float groundLevel;
    protected Sprite sprite;
    protected boolean isPulledBygravity;
    protected boolean onGround;
    protected float gravityMul;



    public BaseParticle(Texture baseTexture){

        this.sprite = new Sprite(baseTexture);
        this.groundLevel = 0; //arbitrary should be changed
        this.isPulledBygravity = false;
        onGround = false;
        this.alpha = 1.0f;
        this.gravityMul = 1.0f;



    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        this.sprite.draw(batch, alpha);

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
            velY += this.gravityMul * Constants.GRAVITY * delta;
        }
        //here we do the fading stuff
        ttl = Math.max(ttl-delta, 0.0f);
        if(ttl <= 0.0f){
            this.alpha -= delta;
            if(alpha < 0.0f){
                this.remove();
            }
        }
    }

    public void setVelInDir(float degrees, float speedAbs){
        degrees = (float)Math.toRadians(degrees);
        velX = (float)Math.cos(degrees) * speedAbs;
        velY = 2*(float)Math.sin(degrees) * speedAbs;
    }
}
