package com.fixthewall.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.fixthewall.game.Helpers;
import com.fixthewall.game.actors.anim.Brixplosion;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.logic.MexicanLogic;

import java.io.Serializable;

/*
 * TARGET ENNEMI BALEZE:    X = [0; 888] et Y = [242; 298]
 *
 * SPAWN ENNEMI BALEZE:
 *                                 fromLeft                     |               !fromLeft
 *                |---------------------------------------------|-----------------------------------------
 *    fromSide    |     X = -(192+50) et Y = [0; 298]           |      X = 1080+50 et Y = [0; 298]
 *   -------------|---------------------------------------------|-----------------------------------------
 *    !fromSide   |     X = [-(192+50); 444] et Y = -(256+50)   |      X = [445; 1080+50] et Y = -(256+50)
 *
 * */

public class EnnemiBaleze extends Actor implements Serializable {

    private final AssetManager ass;
    private TextureRegion hitFrame;
    private TextureRegion previousFrame;
    private TextureRegion currentFrame;
    private float targetX;
    private float targetY;
    private float distance;
    private boolean fromLeft;
    private boolean fromSide;
    //variables d'animation:
    private static final int FRAME_COLS = 3, FRAME_ROWS = 2;
    private Animation<TextureRegion> ennemiBalezeAnimation;
    private Animation<TextureRegion> ennemiBalezeAnimationHit;
    // Variable for tracking elapsed time for the animation
    private float elapsedTime;
    private float elapsedTimeHit;
    private TextureRegion[] ennemiBalezeFramesHit;
    private TextureRegion[] ennemiBalezeFramesWalk;

    private boolean hidden;
    private float timeOffset;

    public EnnemiBaleze(final AssetManager ass) {

        MexicanLogic.getSingleInstance().setEnnemiBalezeCount(MexicanLogic.getSingleInstance().getEnnemiBalezeCount() + 1);

        this.ass = ass;
        setTouchable(Touchable.disabled); // click through
        this.setupTexture();
        this.fromLeft = false;
        this.reset();
        //bricksToutchEnable();

        timeOffset = (float) Helpers.getRandom(1);

    }


    public void reset() {

        //Set animation move

        this.setSide();
        if(fromLeft) {
            flip();
        }

        this.setCoor();
        this.setTarget();
        this.setDistance();

        float duration = getRandom(3) + 11f;
        float frame1Speed = (duration / distance) * 10f;//Vitesse = (distance/temps)^-1 ici car c'est le temps des frames et non la vitesse.
        float randSpeed = getRandom(3) + 10f;
        float frame2Speed = randSpeed / 100f;

        this.addAction(Actions.moveTo(targetX, targetY, duration));
        //
        //Set animation hit

        ennemiBalezeAnimation.setFrameDuration(frame1Speed);
        ennemiBalezeAnimationHit.setFrameDuration(frame2Speed);
        setVisible(true);
    }

    @Override
    public void act(float delta) {
        if (MexicanLogic.getSingleInstance().isDisabledNPCs())
            return;
        if (timeOffset > 0) {
            timeOffset -= delta;
            return;
        }
        super.act(delta);
        if (!isVisible() && !hidden) return;

        if (this.getX() == targetX && this.getY() == targetY) {
            elapsedTimeHit += delta;
            currentFrame = ennemiBalezeAnimationHit.getKeyFrame(elapsedTimeHit, true);
            if (currentFrame == hitFrame && (previousFrame == null || previousFrame != currentFrame)) {
                this.hitTheWall();
            }
            previousFrame = currentFrame;
        }
        else{
            elapsedTime += delta;
            currentFrame = ennemiBalezeAnimation.getKeyFrame(elapsedTime, true);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, this.getX(), this.getY());
    }

    private void setSide() {
        int rand = getRandom(2);
        fromLeft = (rand == 0);

        rand = getRandom(2);
        fromSide = (rand == 0);
    }

    /*
     * Set le point de spawn de cette ennemi.
     * */
    private void setCoor() {
        if (fromLeft) {
            if (fromSide) {// fromLeft && fromSide -> X = -(192+50) et Y = [0; 298]
                this.setX(-(192f + 50f));
                this.setY((float) getRandom(299));
            } else {// fromLeft && !fromSide -> X = [-(192+50); 444] et Y = -(256+50)
                this.setX(-(192f + 50f) + getRandom(687));
                this.setY(-(256f + 50f));
            }
        } else {
            if (fromSide) {// !fromLeft && fromSide -> X = 1080+50 et Y = [0; 298]
                this.setX(1080f + 50f);
                this.setY((float) getRandom(299));
            } else {// !fromLeft && !fromSide -> X = [445; 1080+50] et Y = -(256+50)
                this.setX(445f + getRandom(686));
                this.setY(-(256f + 50f));
            }
        }
    }

    /*
     * Set le point d'arrivé de cette ennemi.
     * */
    private void setTarget() {
        float x = this.getX();
        int interval;
        if (fromLeft) {
            if (x < 0f) {
                interval = 889;
                targetX = (float) getRandom(interval);
            } else {
                interval = 889 - (int) x;
                targetX = x + (float) getRandom(interval);
            }
        } else {
            if (x > 889f)
                interval = 889;
            else
                interval = (int) x;

            targetX = (float) getRandom(interval);
        }
        targetY = 242f + getRandom(getRandom(47));
    }

    public void hide(){
        this.hidden = true;
        setVisible(false);
    }

    public void unhide(){
        this.hidden = false;
        setVisible(true);
    }

    public void kill() {
        if(hidden) return;
        Brixplosion explosion = new Brixplosion(32, ass, getX(), getY(), 0f);
        explosion.setPosition(getX(), getY());
        MexicanLogic.getSingleInstance().getBrixplosionGroup().addActor(explosion);
        setVisible(false);
        MexicanLogic.getSingleInstance().getEnnemiBalezePool().free(this);
        MexicanLogic.getSingleInstance().setEnnemiBalezeCount(
                MexicanLogic.getSingleInstance().getEnnemiBalezeCount() - 1
        );
    }

    private void setDistance() {
        distance = Math.abs(this.getX() - targetX) + Math.abs(this.getY() - targetY);
    }


    private void hitTheWall() {
        MexicanLogic.getSingleInstance().doBalezeDamage();
    }

    /*
     * Retourne un entier aléatoire entre 0 et n-1
     */
    private static int getRandom(int n) {
        return (int) (Math.random() * n);
    }

    private void setupTexture() {
        Texture texture = ass.get("Frames/SheetFrameBaleze.png");
        Texture textureHit = ass.get("Frames/SheetFrameBalezeHit.png");

        // set hitbox for click listener
        this.setWidth(texture.getWidth() / (float) FRAME_COLS);
        this.setHeight(texture.getHeight() / (float) FRAME_ROWS);
        this.setBounds(getX(), getY(), getWidth(), getHeight());
        //

        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);

        TextureRegion[][] tmpHit = TextureRegion.split(textureHit,
                textureHit.getWidth() / FRAME_COLS,
                textureHit.getHeight() / FRAME_ROWS);

        ennemiBalezeFramesWalk = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        ennemiBalezeFramesHit = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                ennemiBalezeFramesWalk[index] = tmp[i][j];
                ennemiBalezeFramesHit[index++] = tmpHit[i][j];
            }
        }

        hitFrame = ennemiBalezeFramesHit[2]; //Frame de frappe
        ennemiBalezeAnimation = new Animation<TextureRegion>(1.0f, ennemiBalezeFramesWalk);
        ennemiBalezeAnimationHit = new Animation<TextureRegion>(1.0f, ennemiBalezeFramesHit);
    }

    private void bricksToutchEnable(){
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    ((Ennemi) event.getListenerActor()).kill();
            }

        });
    }

    public float getTargetX() {
        return targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public void flip(){
        for (int i = 0; i < ennemiBalezeFramesHit.length; i++)
            ennemiBalezeFramesHit[i].flip(true, false);
        for (int i = 0; i < ennemiBalezeFramesWalk.length; i++)
            ennemiBalezeFramesWalk[i].flip(true, false);
    }

}
