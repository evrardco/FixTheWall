package com.fixthewall.game.actors.anim;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.fixthewall.game.actors.physics.BaseParticle;
import com.fixthewall.game.actors.physics.Fire;
import com.fixthewall.game.actors.physics.Smoke;

import static com.badlogic.gdx.math.MathUtils.random;


public class Explosion extends Group {
    private Image[] smokes;
    private int smokeCount;
    private BaseParticle fire;



    public Explosion(float x, float y, AssetManager ass) {
        super();
        this.setPosition(x, y);
        this.fire = new Fire(ass, x, y);
        this.smokeCount = 25;

        for(int i=0; i < smokeCount; i++) this.addActor(new Smoke(x, y, ass, 3,
                0.5f + random()*0.5f));
        this.addActor(fire);

        this.explode();
    }


    public void explode(){

        this.setVisible(true);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

}
