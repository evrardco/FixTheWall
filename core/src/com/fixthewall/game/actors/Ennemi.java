package com.fixthewall.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.fixthewall.game.actors.anim.Brixplosion;
import com.fixthewall.game.logic.MexicanLogic;

/*
* TARGET ENNEMI:    X = [0; 984] et Y = [242; 298]
*
* SPAWN ENNEMI:
*                                 fromLeft                     |               !fromLeft
*                |---------------------------------------------|-----------------------------------------
*    fromSide    |     X = -(96+50) et Y = [0; 298]            |      X = 1080+50 et Y = [0; 298]
*   -------------|---------------------------------------------|-----------------------------------------
*    !fromSide   |     X = [-(96+50); 492] et Y = -(128+50)    |      X = [493; 1080+50] et Y = -(128+50)
*
* */

public class Ennemi extends Actor implements Pool.Poolable {
    private final AssetManager ass;
    private TextureRegion hitFrame;
    private TextureRegion previousFrame;
    private TextureRegion currentFrame;
    private float targetX;
    private float targetY;
    private float distance;
    private boolean isMegaEnnemi;
    private boolean fromLeft;
    private boolean fromSide;
    //variables d'animation:
    private static final int FRAME_COLS = 4, FRAME_ROWS = 5;
    private static final int FRAME_COLS2 = 4, FRAME_ROWS2 = 2;
    private Animation<TextureRegion> ennemiAnimation;
    private Animation<TextureRegion> ennemiAnimationHit;
    // Variable for tracking elapsed time for the animation
    private float elapsedTime;
    private float elapsedTimeHit;
    private TextureRegion[] ennemiFramesHit;
    private TextureRegion[] ennemiFramesWalk;
    private boolean hidden;

    private Rectangle bounds;

    public Ennemi(final AssetManager ass) {
        this.ass = ass;
        this.setupTexture();
        this.fromLeft = false;
        this.reset();
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    ((Ennemi) event.getListenerActor()).kill();
            }

        });

        this.bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }


    @Override
    public void reset() {

        //Set animation move

        boolean tmp = fromLeft;
        this.setSide();
        if(fromLeft != tmp) flip();

        this.setCoor();
        this.setTarget();
        this.setDistance();

        float duration = getRandom(4) + 3f;
        float frame1Speed = (duration / distance) * 4f;//Vitesse = (distance/temps)^-1 ici car c'est le temps des frames et non la vitesse.
        float randSpeed = getRandom(6) + 5f;
        float frame2Speed = randSpeed / 100f;

        this.addAction(Actions.moveTo(targetX, targetY, duration));
        //
        //Set animation hit
        if (isMegaEnnemi) {
            frame2Speed /= 4f;
        }

        ennemiAnimation.setFrameDuration(frame1Speed);
        ennemiAnimationHit.setFrameDuration(frame2Speed);
        setVisible(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!isVisible() && !hidden) return;
        
        if (this.getX() == targetX && this.getY() == targetY) {
            elapsedTimeHit += delta;
            currentFrame = ennemiAnimationHit.getKeyFrame(elapsedTimeHit, true);
            if (currentFrame == hitFrame && (previousFrame == null || previousFrame != currentFrame)) {
                this.hitTheWall();
            }
            previousFrame = currentFrame;
        } else {
            elapsedTime += delta;
            currentFrame = ennemiAnimation.getKeyFrame(elapsedTime, true);
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
        rand = getRandom(100);// 1% de chance d'être MEGA !!!
        isMegaEnnemi = (rand == 42);
    }

    /*
     * Set le point de spawn de cette ennemi.
     * */
    private void setCoor() {
        if (fromLeft) {
            if (fromSide) {// fromLeft && fromSide -> X = -(96+50) et Y = [0; 298]
                this.setX(-(96f + 50f));
                this.setY((float) getRandom(299));
            } else {// fromLeft && !fromSide -> X = [-(96+50); 492] et Y = -(128+50)
                this.setX(-(96f + 50f) + getRandom(639));
                this.setY(-(128f + 50f));
            }
        } else {
            if (fromSide) {// !fromLeft && fromSide -> X = 1080+50 et Y = [0; 298]
                this.setX(1080f + 50f);
                this.setY((float) getRandom(299));
            } else {// !fromLeft && !fromSide -> X = [493; 1080+50] et Y = -(128+50)
                this.setX(493f + getRandom(638));
                this.setY(-(128f + 50f));
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
                interval = 985;
                targetX = (float) getRandom(interval);
            } else {
                interval = 985 - (int) x;
                targetX = x + (float) getRandom(interval);
            }
        } else {
            if (x > 985f)
                interval = 985;
            else
                interval = (int) x;

            targetX = (float) getRandom(interval);
        }
        targetY = 242f + getRandom(getRandom(57));
    }

    public Rectangle getBounds() {
        return bounds.setPosition(getX(), getY());
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
        Brixplosion explosion = new Brixplosion(15, ass, getX(), getY(), 0f);
        explosion.setPosition(getX(), getY());
        getParent().addActor(explosion);
        setVisible(false);
        MexicanLogic.getSingleInstance().pool.free(this);

    }

    private void setDistance() {
        distance = Math.abs(this.getX() - targetX) + Math.abs(this.getY() - targetY);
    }


    private void hitTheWall() {
        MexicanLogic.getSingleInstance().doDamage();
    }

    /*
     * Retourne un entier aléatoire entre 0 et n-1
     */
    private static int getRandom(int n) {
        return (int) (Math.random() * n);
    }

    private void setupTexture() {
        Texture texture = ass.get("Frames/SheetFrameEnnemi.png");

        // set hitbox for click listener
        this.setWidth(texture.getWidth() / (float) FRAME_COLS);
        this.setHeight(texture.getHeight() / (float) FRAME_ROWS);
        this.setBounds(getX(), getY(), getWidth(), getHeight());
        //

        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);

        ennemiFramesWalk = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                ennemiFramesWalk[index++] = tmp[i][j];
            }
        }

        Texture texture2 = ass.get("Frames/SheetFrameEnnemiHit.png");

        TextureRegion[][] tmp2 = TextureRegion.split(texture2,
                texture2.getWidth() / FRAME_COLS2,
                texture2.getHeight() / FRAME_ROWS2);

        ennemiFramesHit = new TextureRegion[FRAME_COLS2 * FRAME_ROWS2];

        int index2 = 0;
        for (int i = 0; i < FRAME_ROWS2; i++) {
            for (int j = 0; j < FRAME_COLS2; j++) {
                ennemiFramesHit[index2++] = tmp2[i][j];
            }
        }

        hitFrame = ennemiFramesHit[3]; //Frame de frappe
        ennemiAnimation = new Animation<TextureRegion>(1.0f, ennemiFramesWalk);
        ennemiAnimationHit = new Animation<TextureRegion>(1.0f, ennemiFramesHit);
    }

    /*
    @pre: setupTextures has been called once
    @post: texture have been switched according to dir
     */


    public void flip(){
        for (int i = 0; i < ennemiFramesHit.length; i++)
            ennemiFramesHit[i].flip(true, false);
        for (int i = 0; i < ennemiFramesWalk.length; i++)
            ennemiFramesWalk[i].flip(true, false);
    }
}
