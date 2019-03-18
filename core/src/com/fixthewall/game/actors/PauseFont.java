package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PauseFont extends Actor {

    private Texture texture;

    public PauseFont (AssetManager ass) {

        super();
        texture = ass.get("imgPauseFont.png", Texture.class);
        setHeight(texture.getHeight());
        setWidth(texture.getWidth());
        setPosition(0, 0);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

}
