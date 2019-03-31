package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.fixthewall.game.logic.GameLogic;

public class Sun extends Actor {

    private TextureRegion texture;
    private static final float TARGET = -3200f;
    private static final float TIME = 160f;
    private Rectangle bounds;

    public Sun (AssetManager ass){
        //set position
        setCoor();
        texture = new TextureRegion(ass.get("Sun.png", Texture.class));
        this.setWidth(texture.getRegionWidth());
        this.setHeight(texture.getRegionWidth());
        this.bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        setVisible(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (GameLogic.getSingleInstance().getTrumpTime() > 0)
        {
            GameLogic.getSingleInstance().setTrumpTime(GameLogic.getSingleInstance().getTrumpTime()-delta);
            setVisible(true);
        }
        else {
            setVisible(false);
        }
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
