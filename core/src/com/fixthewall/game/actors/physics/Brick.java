package com.fixthewall.game.actors.physics;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Brick extends Actor {
    private float y;
    private float x;
    private float velX;
    private float velY;
    private float angVel;
    private float ttl;
    private Sprite sprite;
    private boolean isPulledBygravity;
    private boolean onGround;



    public Brick(float x, float y, float velX, float velY, float angVel, float ttl, boolean gravity, AssetManager ass){
        this.velX = velX;
        this.velY = velY;
        this.angVel = angVel;
        this.ttl = ttl;
        this.isPulledBygravity = gravity;
        this.sprite = new Sprite((Texture)ass.get("anim/brick.png"));
        this.sprite.setPosition(x, y);
        onGround = false;
        this.x = x;
        this.y = y;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setPosition(sprite.getX(), sprite.getY());
        sprite.translate(velX * delta, velY * delta);
        sprite.rotate(angVel * delta);
        ttl -= delta;

        if(!onGround && sprite.getY() <= Constants.GROUND_LEVEL){
            velX = 0;
            sprite.setRotation(0f);
            angVel = 0.0f;
            onGround = true;
        }if(onGround){
            velX *= Constants.SLOWING_FACTOR;
        } else if(isPulledBygravity){
            velY += Constants.GRAVITY * delta;
        }
    }

    public void setVelInDir(float degrees, float speedAbs){
        velX = (float)Math.cos(degrees * speedAbs);
        velY = (float)Math.sin(degrees * speedAbs);
    }

}
