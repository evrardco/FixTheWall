package com.fixthewall.game.actors.physics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.sin;
import static java.lang.Math.PI;
import static java.lang.Math.random;
public class Smoke extends BaseParticle {
    private float lastEmmited;
    private boolean generator;
    private float timeElapsed;
    private float freq;
    private float baseScale;
    private AssetManager ass;

    public Smoke(float x, float y, AssetManager ass, float freq, float baseScale){
        super(ass.get("anim/explosion/smoke.png", Texture.class));
        this.x = x;
        this.y = y;
        float velIntensity = (float)random()*500f;
        float minAngle = (float)PI/6;
        float maxAngle = 2*(float)PI - (float)PI/6;
        float θ = minAngle + (float)random() * (maxAngle - minAngle);
        this.ass = ass;
        this.freq = freq;
        this.velX = velIntensity*cos(θ);
        this.velY = velIntensity*sin(θ)*2;

        this.angVel = (float)random()*360f;

        this.ttl = (float)random()*0.1f;
        this.isPulledBygravity = true;
        this.gravityMul = 0.2f;
        this.timeElapsed = 0.0f;
        this.lastEmmited = 0.0f;
        this.sprite.setScale(baseScale);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        this.timeElapsed += delta;
        if(false && this.getParent() != null && freq > 0 && timeElapsed - lastEmmited > 1/freq){
            Vector2 center = new Vector2();
            sprite.getBoundingRectangle().getCenter(center);
            this.getParent().addActor(new Smoke(
                    center.x,
                    center.y,
                    ass,
                    0,
                    this.sprite.getScaleX()*0.8f
                )
            );
            this.lastEmmited = timeElapsed;
        }

    }
}
