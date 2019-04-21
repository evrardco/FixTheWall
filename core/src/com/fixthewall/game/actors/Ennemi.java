package com.fixthewall.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Pool;
import com.fixthewall.game.actors.anim.Brixplosion;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.logic.MexicanLogic;

import java.io.Serializable;

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

public class Ennemi extends Actor implements Serializable {
    private final AssetManager ass;
    private TextureRegion hitFrame;
    private TextureRegion previousFrame;
    private TextureRegion currentFrame;
    private float targetX;
    private float targetY;
    private float spoonPointX;
    private float spoonPointY;
    private float distance;
    private boolean isMegaEnnemi;
    private boolean fromLeft;
    private boolean fromSide;
    private boolean isDragged;
    private Vector2 position;
    //variables d'animation:
    private static final int FRAME_COLS = 4, FRAME_ROWS = 5;
    private static final int FRAME_COLS2 = 4, FRAME_ROWS2 = 2;
    private Animation<TextureRegion> ennemiAnimation;
    private Animation<TextureRegion> ennemiAnimationCash;
    private Animation<TextureRegion> ennemiAnimationHit;
    // Variable for tracking elapsed time for the animation
    private float elapsedTime;
    private float elapsedTimeCash;
    private float elapsedTimeHit;
    private TextureRegion[] ennemiFramesHit;
    private TextureRegion[] ennemiFramesWalk;
    private TextureRegion[] ennemiFramesWalkCash;
    private boolean hidden;

    private boolean isPayed;
    private boolean payedIsSet;
    private float durationCash;

    private Rectangle bounds;


    public Ennemi(final AssetManager ass) {

        MexicanLogic.getSingleInstance().setEnnemiCount(
                MexicanLogic.getSingleInstance().getEnnemiToRemove()+1
        );

        this.ass = ass;
        this.setupTexture();
        this.fromLeft = false;
        position = new Vector2();
        this.reset();

        //bricksToutchEnable();

        this.isPayed = false;
        this.payedIsSet = false;
        this.isDragged = false;
        this.addListener((new DragListener() {
            public void touchDragged (InputEvent event, float x, float y, int pointer) {
                moveBy(x - getWidth()/2, y - getHeight()/2);
                System.out.println("touchdragged" + x + ", " + y);

            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (GameLogic.getSingleInstance().getTrumpTime()>0) {
                    isDragged = true;
                    position.x = getX();
                    position.y = getY();
                    System.out.println("touchdown" + x + ", " + y);
                    return true;
                }
                return false;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                if(MexicanLogic.getSingleInstance().checkTrumpCollision(getBounds()))
                {
                    kill();
                }
                else {
                    if (isDragged) {
                        setPosition(position.x, position.y);
                    }
                }
                isDragged = false;
                System.out.println("touchup" + x + ", " + y);
            }
        }));


        this.bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }


    public void reset() {

        //Set animation move

        this.setSide();
        if(fromLeft){
            flip();
        }
        else{
            flipCash();
        }

        this.setCoor();
        this.setTarget();
        this.setDistance();

        float duration = getRandom(4) + 3f;
        float frame1Speed = (duration / distance) * 4f;//Vitesse = (distance/temps)^-1 ici car c'est le temps des frames et non la vitesse.
        float randSpeed = getRandom(6) + 5f;
        float frame2Speed = randSpeed / 100f;

        this.addAction(Actions.moveTo(targetX, targetY, duration));
        this.durationCash = duration;
        //
        //Set animation hit
        if (isMegaEnnemi) {
            frame2Speed /= 4f;
        }

        ennemiAnimation.setFrameDuration(frame1Speed);
        ennemiAnimationCash.setFrameDuration(frame1Speed);
        ennemiAnimationHit.setFrameDuration(frame2Speed);
        setVisible(true);
    }

    @Override
    public void act(float delta) {
        if (!isDragged) {
            super.act(delta);
            if (!isVisible() && !hidden) return;
            if(isPayed){
                if(!payedIsSet){
                    setActionCash();
                    payedIsSet = true;
                }
                returnWithCash(delta);
            }
            else{
                if (this.getX() == targetX && this.getY() == targetY) {
                    elapsedTimeHit += delta;
                    currentFrame = ennemiAnimationHit.getKeyFrame(elapsedTimeHit, true);
                    if (currentFrame == hitFrame && (previousFrame == null || previousFrame != currentFrame)) {
                        this.hitTheWall();
                    }
                    previousFrame = currentFrame;
                }
                else{
                    elapsedTime += delta;
                    currentFrame = ennemiAnimation.getKeyFrame(elapsedTime, true);
                }
            }
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
                spoonPointX = this.getX();
                this.setY((float) getRandom(299));
                spoonPointY = this.getY();
            } else {// fromLeft && !fromSide -> X = [-(96+50); 492] et Y = -(128+50)
                this.setX(-(96f + 50f) + getRandom(639));
                spoonPointX = this.getX();
                this.setY(-(128f + 50f));
                spoonPointY = this.getY();
            }
        } else {
            if (fromSide) {// !fromLeft && fromSide -> X = 1080+50 et Y = [0; 298]
                this.setX(1080f + 50f);
                spoonPointX = this.getX();
                this.setY((float) getRandom(299));
                spoonPointY = this.getY();
            } else {// !fromLeft && !fromSide -> X = [493; 1080+50] et Y = -(128+50)
                this.setX(493f + getRandom(638));
                spoonPointX = this.getX();
                this.setY(-(128f + 50f));
                spoonPointY = this.getY();
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
        Brixplosion explosion = new Brixplosion(32, ass, getX(), getY(), 0f);
        explosion.setPosition(getX(), getY());
        getParent().addActor(explosion);
        setVisible(false);
        MexicanLogic.getSingleInstance().pool.free(this);
        MexicanLogic.getSingleInstance().setEnnemiCount(
                MexicanLogic.getSingleInstance().getEnnemiToRemove()-1
        );

    }

    public void setPayed(){
        this.isPayed = true;
    }

    private void setActionCash(){
        this.addAction(Actions.moveTo(spoonPointX, spoonPointY, durationCash));
    }

    private void returnWithCash(float delta){
        if (this.getX() == spoonPointX && this.getY() == spoonPointY) {
            //free
            MexicanLogic.getSingleInstance().pool.free(this);
            MexicanLogic.getSingleInstance().setEnnemiCount(
                    MexicanLogic.getSingleInstance().getEnnemiToRemove()-1
            );
        }
        else{
            elapsedTimeCash += delta;
            currentFrame = ennemiAnimationCash.getKeyFrame(elapsedTimeCash, true);
        }
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
        Texture textureCash = ass.get("Frames/SheetFrameCash.png");

        // set hitbox for click listener
        this.setWidth(texture.getWidth() / (float) FRAME_COLS);
        this.setHeight(texture.getHeight() / (float) FRAME_ROWS);
        this.setBounds(getX(), getY(), getWidth(), getHeight());
        //

        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);

        TextureRegion[][] tmpCash = TextureRegion.split(textureCash,
                textureCash.getWidth() / FRAME_COLS,
                textureCash.getHeight() / FRAME_ROWS);

        ennemiFramesWalk = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        ennemiFramesWalkCash = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                ennemiFramesWalk[index] = tmp[i][j];
                ennemiFramesWalkCash[index++] = tmpCash[i][j];
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
        ennemiAnimationCash = new Animation<TextureRegion>(1.0f, ennemiFramesWalkCash);
        ennemiAnimationHit = new Animation<TextureRegion>(1.0f, ennemiFramesHit);
    }

    private void bricksToutchEnable(){
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Ennemi) event.getListenerActor()).kill();
            }

        });
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

    public void flipCash(){
        for (int i = 0; i < ennemiFramesWalkCash.length; i++)
            ennemiFramesWalkCash[i].flip(true, false);
    }
}
