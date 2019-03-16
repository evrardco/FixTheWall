package com.fixthewall.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.assets.AssetManager;

public class PauseButton extends Actor {

    private Texture texture;

    public PauseButton (AssetManager ass) {

        super();
        texture = ass.get("imgPause.png", Texture.class);
        setHeight(texture.getHeight());
        setWidth(texture.getWidth());
        setPosition(30, 1810);

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
