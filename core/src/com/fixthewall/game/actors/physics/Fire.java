package com.fixthewall.game.actors.physics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import static com.badlogic.gdx.math.MathUtils.random;

public class Fire extends BaseParticle {

    public Fire(AssetManager ass, float x, float y){
        super(ass.get("anim/explosion/flame.png", Texture.class));
        this.ttl = random()*0.05f;
        this.isPulledBygravity = false;
        this.velX = this.angVel = this.velY = 0.0f;
        this.x = x;
        this.y = y;
    }
}
