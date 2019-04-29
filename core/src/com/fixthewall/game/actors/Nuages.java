package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Nuages extends Actor {

    private TextureRegion texture;
    private static final float TARGET = -3200f;
    private static final float TIME = 250f;

    public Nuages (AssetManager ass){
        //set position
        setCoor();
        setTouchable(Touchable.disabled); // clik through

        texture = new TextureRegion(ass.get("nuages.png", Texture.class));

        this.addAction(Actions.moveTo(TARGET, this.getY(), TIME));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.getX() == TARGET){
            this.setX(1080f);
            this.addAction(Actions.moveTo(TARGET, this.getY(), TIME));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY());
    }

    private void setCoor(){
        this.setX(1080f);
        this.setY(1500f);
    }

}
