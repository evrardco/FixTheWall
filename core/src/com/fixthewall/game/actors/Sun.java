package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fixthewall.game.logic.GameLogic;

public class Sun extends Actor {

    private TextureRegion texture;
    private TextureRegion textureTrump;
    private TextureRegion textureSun;
    private Rectangle bounds;
    private Boolean isTrump;

    public Sun (AssetManager ass){
        //set position
        setCoor();
        textureSun = new TextureRegion(ass.get("Sun.png", Texture.class));
        textureTrump = new TextureRegion(ass.get("trump.png", Texture.class));
        texture = textureSun;
        isTrump = false;
        this.setWidth(texture.getRegionWidth());
        this.setHeight(texture.getRegionWidth());
        this.bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        setVisible(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (GameLogic.getSingleInstance().getTrumpTime() > 0)
        {
            if (!isTrump) {
                isTrump = true;
                switchTexture();
            }
            GameLogic.getSingleInstance().setTrumpTime(GameLogic.getSingleInstance().getTrumpTime()-delta);
        }
        else {
            if (isTrump)
            {
                switchTexture();
            }
            isTrump = false;

        }
    }

    public void switchTexture() {
        if (texture == textureSun)
        {
            texture = textureTrump;
        }
        else {
            texture = textureSun;
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
