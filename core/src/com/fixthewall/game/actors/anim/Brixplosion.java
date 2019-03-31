package com.fixthewall.game.actors.anim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.fixthewall.game.actors.physics.Brick;

public class Brixplosion extends Actor{
    public static final int MAX_BRICKS_COUNT = 64;
    public static int brickCount = 0;
    private Group bricks;
    private float alpha;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        bricks.draw(batch, parentAlpha);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        bricks.act(delta);

    }

    public Brixplosion(int count, AssetManager ass, float x, float y, float baseSpeed){
        bricks = new Group();
        alpha = 1f;
        for(int i=0; i < count && brickCount < MAX_BRICKS_COUNT; i ++){
            float angVel = (float)Math.random()*360f;
            float dir = (float)(30.0f    +Math.random()*150f %150f);
            float ttl = (float)(1.0f +Math.random()*3f % 3f);
            float speed = (float)Math.random()*4000.0f - 2000.0f;
            Brick brick = new Brick(x, y,0.0f, 0.0f, angVel, ttl, true, ass);
            brick.setVelInDir(dir, baseSpeed + speed);
            bricks.addActor(brick);
        }
    }



}
