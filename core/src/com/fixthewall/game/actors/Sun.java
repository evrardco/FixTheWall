package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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


    private TextureRegion currentFrame;
    private Animation<TextureRegion> sunAnimation;
    private static final int FRAME_COLS = 4, FRAME_ROWS = 4;
    private float elapsedTime;
    private TextureRegion[] sunFrames;

    public Sun (AssetManager ass) {


        //background's    0.5<alpha<1 -> night time
        //set position
        textureSun = new TextureRegion(ass.get("Sun.png", Texture.class));
        textureTrump = new TextureRegion(ass.get("trump.png", Texture.class));
        texture = textureSun;

        Texture sheet = ass.get("Frames/SheetFrameSun.png");
        isTrump = false;
        this.setWidth(sheet.getWidth() / (float) FRAME_COLS);
        this.setHeight(sheet.getHeight() / (float) FRAME_ROWS);
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / FRAME_COLS, sheet.getHeight() / FRAME_ROWS);
        sunFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        this.bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        setVisible(true);
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                sunFrames[index++] = tmp[i][j];
            }
        }
        elapsedTime = 0;
        sunAnimation = new Animation<TextureRegion>(0.07f, sunFrames);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        /*
        float a = background.getColor().a;
        float diff = abs(prevBackgroundAlpha - a)*(float)PI;
        prevBackgroundAlpha = a;
        alpha += diff;
        float pi2 = (float)PI/2;
        float x = center.x + radius * (float)cos(alpha + pi2);
        float y = center.y + radius * (float)sin(alpha + pi2);
        this.setPosition(x, y);
        */
        if (GameLogic.getSingleInstance().getTrumpTime() > 0)
        {
            elapsedTime += delta;
            texture = sunAnimation.getKeyFrame(elapsedTime, false);
            if (!isTrump) {
                isTrump = true;
            }
            GameLogic.getSingleInstance().setTrumpTime(GameLogic.getSingleInstance().getTrumpTime()-delta);
        }
        else {
            if (isTrump)
            {
                texture = textureSun;
                elapsedTime = 0;
                isTrump = false;
            }

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
