package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.fixthewall.game.Helpers;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.logic.MexicanLogic;

public class Sun extends Actor {

    private TextureRegion textureSun;
    private Rectangle bounds;
    private Boolean isTrump;

    private AssetManager ass;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> sunAnimation;
    private static final int FRAME_COLS = 4, FRAME_ROWS = 4;
    private float elapsedTime;
    private float elapsedTimeLaser;

    public Sun (AssetManager ass) {
        this.ass = ass;

        //background's    0.5<alpha<1 -> night time
        //set position
        textureSun = new TextureRegion(ass.get("Sun.png", Texture.class));
        currentFrame = textureSun;

        Texture sheet = ass.get("Frames/SheetFrameSun.png");
        isTrump = false;
        this.setWidth(sheet.getWidth() / (float) FRAME_COLS);
        this.setHeight(sheet.getHeight() / (float) FRAME_ROWS);
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / FRAME_COLS, sheet.getHeight() / FRAME_ROWS);
        TextureRegion[] sunFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        this.bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        setVisible(true);
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                sunFrames[index++] = tmp[i][j];
            }
        }
        elapsedTime = 0;
        elapsedTimeLaser = 0;
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
        if (GameLogic.getSingleInstance().getTrumpTime() > 0) {
            elapsedTime += delta;
            elapsedTimeLaser += delta;
            currentFrame = sunAnimation.getKeyFrame(elapsedTime, false);
            if (!isTrump) {
                isTrump = true;
            }
            GameLogic.getSingleInstance().setTrumpTime(GameLogic.getSingleInstance().getTrumpTime()-delta);
            if (elapsedTimeLaser >= 1f) {
                shootLasers();
                elapsedTimeLaser = 0;
            }
        } else if (isTrump) {
            currentFrame = textureSun;
            elapsedTime = 0;
            isTrump = false;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, this.getX(), this.getY());
    }

    private void shootLasers() {
        if (MexicanLogic.getSingleInstance().getPool().getShown().size < 1)
            return;
        final int target = (int) Helpers.getRandom(MexicanLogic.getSingleInstance().getPool().getShown().size);
        Ennemi targetEnnemi = MexicanLogic.getSingleInstance().getPool().getShown().get(target);
        float targetX = targetEnnemi.getX() + targetEnnemi.getWidth() / 2f;
        float targetY = targetEnnemi.getY() + targetEnnemi.getHeight() / 2f;

        final Laser laserLeft = new Laser(ass, getX() + getWidth() / 4f + 35, getY() + getHeight() / 4f - 5, targetX, targetY);
        final Laser laserRight = new Laser(ass, getX() + getWidth() / 2f + 35, getY() + getHeight() / 4f - 5, targetX, targetY);

        MexicanLogic.getSingleInstance().getLaserGroup().addActor(laserLeft);
        MexicanLogic.getSingleInstance().getLaserGroup().addActor(laserRight);
    }

    private void setCoor() {
        this.setX(900);
        this.setY(1400f);
    }

    public Rectangle getBounds()
    {
        return bounds.setPosition(getX(), getY());
    }


}
