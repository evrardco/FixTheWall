package com.fixthewall.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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

public class Ennemi extends Actor {

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
    private Rectangle bounds;

    public Ennemi (AssetManager ass){
        this.setSide();
        this.setCoor();
        this.setTarget();
        this.setDistance();
        setTouchable(Touchable.enabled); // click through

        //Set animation move
        Texture texture = ass.get("Frames/SheetFrameEnnemi.png");

        // set hitbox for click listener
        this.setWidth(texture.getWidth() / (float) FRAME_COLS);
        this.setHeight(texture.getHeight() / (float) FRAME_ROWS);
        this.setBounds(getX(), getY(), getWidth(), getHeight());
        //

        TextureRegion[][] tmp = TextureRegion.split(texture,
                        texture.getWidth() / FRAME_COLS,
                        texture.getHeight() / FRAME_ROWS);
        TextureRegion[] ennemiFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                if(fromLeft) {
                    TextureRegion flipped = tmp[i][j];
                    flipped.flip(true, false);
                    ennemiFrames[index++] = flipped;
                }
                else{
                    ennemiFrames[index++] = tmp[i][j];
                }
            }
        }
        float duration = getRandom(4)+3f;
        float frame1Speed = (duration/distance)*4f;//Vitesse = (distance/temps)^-1 ici car c'est le temps des frames et non la vitesse.
        ennemiAnimation = new Animation<TextureRegion>(frame1Speed, ennemiFrames);
        this.addAction(Actions.moveTo(targetX, targetY, duration));
        //
        //Set animation hit
        Texture texture2 = ass.get("Frames/SheetFrameEnnemiHit.png");
        TextureRegion[][] tmp2 = TextureRegion.split(texture2,
                texture2.getWidth() / FRAME_COLS2,
                texture2.getHeight() / FRAME_ROWS2);
        TextureRegion[] ennemiFrames2 = new TextureRegion[FRAME_COLS2 * FRAME_ROWS2];
        int index2 = 0;
        for (int i = 0; i < FRAME_ROWS2; i++) {
            for (int j = 0; j < FRAME_COLS2; j++) {
                if(fromLeft) {
                    TextureRegion flipped = tmp2[i][j];
                    flipped.flip(true, false);
                    ennemiFrames2[index2++] = flipped;
                }
                else{
                    ennemiFrames2[index2++] = tmp2[i][j];
                }
            }
        }
        hitFrame = ennemiFrames2[3]; //Frame de frappe
        float randSpeed = getRandom(6)+5f;
        float frame2Speed = randSpeed/100f;
        if(isMegaEnnemi){
            frame2Speed /= 4f;
        }
        ennemiAnimationHit = new Animation<TextureRegion>(frame2Speed, ennemiFrames2);
        //
    }

    @Override
    public void act(float delta) {
        super.act(delta);
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

    private void setSide(){
        int rand = getRandom(2);
        fromLeft = (rand==0);
        rand = getRandom(2);
        fromSide = (rand==0);
        rand = getRandom(100);// 1% de chance d'être MEGA !!!
        isMegaEnnemi = (rand==42);
    }

    /*
    * Set le point de spawn de cette ennemi.
    * */
    private void setCoor(){
        if(fromLeft){
            if(fromSide){// fromLeft && fromSide -> X = -(96+50) et Y = [0; 298]
                this.setX(-(96f+50f));
                this.setY((float)getRandom(299));
            }
            else{// fromLeft && !fromSide -> X = [-(96+50); 492] et Y = -(128+50)
                this.setX(-(96f+50f)+getRandom(639));
                this.setY(-(128f+50f));
            }
        }
        else{
            if(fromSide){// !fromLeft && fromSide -> X = 1080+50 et Y = [0; 298]
                this.setX(1080f+50f);
                this.setY((float)getRandom(299));
            }
            else{// !fromLeft && !fromSide -> X = [493; 1080+50] et Y = -(128+50)
                this.setX(493f+getRandom(638));
                this.setY(-(128f+50f));
            }
        }
    }

    /*
     * Set le point d'arrivé de cette ennemi.
     * */
    private void setTarget(){
        float x = this.getX();
        int interval;
        if(fromLeft){
            if(x < 0f) {
                interval = 985;
                targetX =(float)getRandom(interval);
            }
            else {
                interval = 985 - (int) x;
                targetX = x + (float)getRandom(interval);
            }
        }
        else{
            if(x > 985f)
                interval = 985;
            else
                interval = (int)x;

            targetX = (float)getRandom(interval);
        }
        targetY = 242f+getRandom(getRandom(57));
    }

    public Rectangle getBounds()
    {
        bounds=new Rectangle((this.getX()+100), this.getY()+100, (this.getWidth()+100), this.getHeight());
        return bounds;

    }
    private void setDistance(){
        distance = Math.abs(this.getX() - targetX) + Math.abs(this.getY() - targetY);
    }

    private void hitTheWall(){
        MexicanLogic.getSingleInstance().doDamage();
    }

    /*
     * Retourne un entier aléatoire entre 0 et n-1
     */
    private static int getRandom(int n){
        return (int)(Math.random()*n);
    }

}
