package com.fixthewall.game.actors.physics;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.fixthewall.game.actors.anim.Brixplosion;

public class Brick extends BaseParticle {
    public Brick(float x, float y, float velX, float velY, float angVel, float ttl, boolean gravity, AssetManager ass){
        super(ass.get("anim/brick.png", Texture.class));
        this.x = x;
        this.y = y;
        this.groundLevel = this.y - 100f; //arbitrary should be changed
        this.velX = velX;
        this.velY = velY;
        this.angVel = angVel;
        this.ttl = ttl;
        this.isPulledBygravity = true;
        this.sprite.setPosition(x, y);
        onGround = false;
        this.alpha = 1.0f;
        Brixplosion.brickCount++;
        sprite.setScale(0.75f);
    }

    @Override
    public boolean remove(){
        Brixplosion.brickCount--;
        return super.remove();
    }

}
