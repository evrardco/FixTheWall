package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.fixthewall.game.logic.GameLogic;


import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.floorDiv;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class Sun extends Actor {

    private float alpha;
    private TextureRegion texture;
    private Rectangle bounds;
    private Vector2 center;
    private float radius;
    private Image background;
    private float prevBackgroundAlpha;
    public Sun (AssetManager ass, float xMidLeft, float yLMidLeft, float radius, Image background){
        //background's    0.5<alpha<1 -> night time
        //set position
        setCoor();
        texture = new TextureRegion(ass.get("Sun.png", Texture.class));
        this.setWidth(texture.getRegionWidth());
        this.setHeight(texture.getRegionWidth());
        this.center = new Vector2(xMidLeft, yLMidLeft);
        this.background = background;
        this.radius = radius;
        this.prevBackgroundAlpha = 0.0f;
        this.alpha = (float)PI;
        this.bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        setVisible(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        float a = background.getColor().a;
        float diff = abs(prevBackgroundAlpha - a)*(float)PI;
        prevBackgroundAlpha = a;
        alpha += diff;
        float pi2 = (float)PI/2;
        float x = center.x + radius * (float)cos(alpha + pi2);
        float y = center.y + radius * (float)sin(alpha + pi2);
        this.setPosition(x, y);

//        if (GameLogic.getSingleInstance().getTrumpTime() > 0)
//        {
//            GameLogic.getSingleInstance().setTrumpTime(GameLogic.getSingleInstance().getTrumpTime()-delta);
//            setVisible(true);
//        }
//        else {
//            setVisible(false);
//        }
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
