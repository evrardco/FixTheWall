package com.fixthewall.game.actors.anim;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.fixthewall.game.actors.physics.Brick;

public class Brixplosion extends Actor{
    private Group bricks;
    private float alpha;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        bricks.draw(batch, alpha+1); //+1 so that it stays visible a little longer
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        alpha -= 0.1*delta;
        bricks.act(delta);
        if(alpha+1 <= 0.01f){
            this.remove();
        }

    }

    public Brixplosion(int count, AssetManager ass, float x, float y){
        bricks = new Group();
        alpha = 1f;
        for(int i=0; i < count; i ++){
            float angVel = (float)Math.random()*360f;
            float dir = (float)Math.random()*360f;
            float ttl = (float)(1.0f +Math.random()*3f % 3f);
            float speed = (float)Math.random()*4000.f - 2000f;
            Brick brick = new Brick(x, y,0.0f, 0.0f, angVel, ttl, true, ass);
            brick.setVelInDir(dir, speed);
            bricks.addActor(brick);
        }
    }



}
