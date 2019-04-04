package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fixthewall.game.logic.GameLogic;

public class Moon extends Actor {

    private TextureRegion texture;
    private Rectangle bounds;

    public Moon (AssetManager ass){
        //set position
        setCoor();
        texture = new TextureRegion(ass.get("moon2.png", Texture.class));
        this.setWidth(texture.getRegionWidth());
        this.setHeight(texture.getRegionWidth());
        this.bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        setVisible(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY());
    }

    private void setCoor(){
        this.setX(900);
        this.setY(1400f);
    }

    public Rectangle getBounds()
    {
        return bounds.setPosition(getX(), getY());
    }

}

