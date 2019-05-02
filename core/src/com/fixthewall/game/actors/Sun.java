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
import com.fixthewall.game.upgrades.UpgradeManager;

public class Sun extends Actor {

    private TextureRegion textureSun;
    private Rectangle bounds;
    private boolean isTrump;
    private boolean wasTrump;

    private AssetManager ass;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> sunAnimation;
    private static final int FRAME_COLS = 4, FRAME_ROWS = 4;
    private float elapsedTime;
    private float elapsedTimeLaser;
    private float timeBetweenShots;
    private boolean isTrumpDisabled;

    public Sun (AssetManager ass) {
        this.ass = ass;

        textureSun = new TextureRegion(ass.get("Sun.png", Texture.class));
        currentFrame = textureSun;

        Texture sheet = ass.get("Frames/SheetFrameSun.png");
        isTrump = false;
        wasTrump = false;
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
        timeBetweenShots = 10f - 0.25f * UpgradeManager.getSingleInstance().getAllUpgrade()[6].getLevel();
        if (timeBetweenShots < 0.5f)
            timeBetweenShots = 0.5f;

        isTrumpDisabled = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!GameLogic.getSingleInstance().isDay())
            GameLogic.getSingleInstance().setTrumpTime(0);

        if (!isTrumpDisabled && GameLogic.getSingleInstance().getTrumpTime() > 0) {
            elapsedTime += delta;
            elapsedTimeLaser += delta;
            sunAnimation.setPlayMode(Animation.PlayMode.NORMAL);
            currentFrame = sunAnimation.getKeyFrame(elapsedTime, false);
            if (!isTrump) {
                isTrump = true;
            }
            GameLogic.getSingleInstance().setTrumpTime(GameLogic.getSingleInstance().getTrumpTime() - delta);

            timeBetweenShots = 10f - 0.25f * UpgradeManager.getSingleInstance().getAllUpgrade()[6].getLevel();
            if (timeBetweenShots < 0.5f)
                timeBetweenShots = 0.5f;
            if (elapsedTimeLaser >= timeBetweenShots) {
                shootLasers();
                elapsedTimeLaser = 0;
            }
        } else if (isTrump) {
            wasTrump = true;
            isTrump = false;
            elapsedTime = 0;
        }
        if (wasTrump) {
            //animation inverse
            elapsedTime += delta;
            sunAnimation.setPlayMode(Animation.PlayMode.REVERSED);
            currentFrame = sunAnimation.getKeyFrame(elapsedTime, false);

            if (sunAnimation.isAnimationFinished(elapsedTime)) {
                wasTrump = false;
                elapsedTime = 0;
                currentFrame = textureSun;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, this.getX(), this.getY());
    }

    private void shootLasers() {
        if (MexicanLogic.getSingleInstance().getEnnemiPool().getShown().size < 1)
            return;

        int target = (int) Helpers.getRandom(MexicanLogic.getSingleInstance().getEnnemiPool().getShown().size);
        Ennemi targetEnnemi = MexicanLogic.getSingleInstance().getEnnemiPool().getShown().get(target);
        float targetX = targetEnnemi.getX() + targetEnnemi.getWidth() / 2f;
        float targetY = targetEnnemi.getY() + targetEnnemi.getHeight() / 2f;

        Laser laserLeft = new Laser(ass, getX() + getWidth() / 4f + 35, getY() + getHeight() / 2f + 5, targetX, targetY);
        Laser laserRight = new Laser(ass, getX() + getWidth() / 2f + 35, getY() + getHeight() / 2f + 5, targetX, targetY);

        MexicanLogic.getSingleInstance().getLaserGroup().addActor(laserLeft);
        MexicanLogic.getSingleInstance().getLaserGroup().addActor(laserRight);
    }

    private void setCoor() {
        this.setX(900);
        this.setY(1400f);
    }

    public Rectangle getBounds() {
        return bounds.setPosition(getX(), getY());
    }

    public void setTrumpDisabled(boolean trumpDisabled) {
        isTrumpDisabled = trumpDisabled;
    }
}
