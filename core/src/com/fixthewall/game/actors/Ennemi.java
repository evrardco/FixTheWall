package com.fixthewall.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/*
* Ennemis avance entre Y = [242; 298] (intervale de 56 pixels)
*  et s'arrêt dans l'intervale X = [0; 984] (intervale de 984 pixels)
* */

public class Ennemi extends Actor {

    private float power;
    private float targetX;
    private int level;
    private boolean fromLeft;
    //variables d'animation:
    private static final int FRAME_COLS = 4, FRAME_ROWS = 5;
    private static final int FRAME_COLS2 = 4, FRAME_ROWS2 = 2;
    private Animation<TextureRegion> ennemiAnimation;
    private Animation<TextureRegion> ennemiAnimationHit;
    private Texture texture;
    private Texture texture2;
    // Variable for tracking elapsed time for the animation
    private float elapsedTime;
    private float elapsedTimeHit;


    public Ennemi (int level){
        if (level < 1)
            this.level = 1;
        else
            this.level = level;
        this.setPower();
        this.setSide();
        this.setCoor();
        setTouchable(Touchable.disabled); // clik through

        //Set animation move
        texture = new Texture("Frames/SheetFrameEnnemi.png");
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
        targetX = (float)getRandom(985);
        float distance = Math.abs(targetX-this.getX());
        float frame1Speed = (duration/distance)*4f;//Vitesse = (distance/temps)^-1 ici car c'est le temps des frames et non la vitesse.
        ennemiAnimation = new Animation<TextureRegion>(frame1Speed, ennemiFrames);
        this.addAction(Actions.moveTo(targetX, this.getY(), duration));
        //
        //Set animation hit
        texture2 = new Texture("Frames/SheetFrameEnnemiHit.png");
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
        float randSpeed = getRandom(8)+5f;
        float frame2Speed = randSpeed/100f;
        ennemiAnimationHit = new Animation<TextureRegion>(frame2Speed, ennemiFrames2);
        //
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(this.getX()==targetX){
            elapsedTimeHit += Gdx.graphics.getDeltaTime();
            batch.draw( ennemiAnimationHit.getKeyFrame(elapsedTimeHit, true), this.getX(), this.getY());
        }
        else{
            elapsedTime += Gdx.graphics.getDeltaTime();
            batch.draw( ennemiAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
        }
    }

    private void setPower() {
        // TODO equilibrage de ça
        this.power = 1.5f * this.level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.setPower();
    }

    private void setSide(){
        int rand = getRandom(2);
        fromLeft = (rand==0);
    }

    private void setCoor(){
        float randY = (float)getRandom(57);
        if(fromLeft){
            this.setX(-(96f+50f));
        }
        else{
            this.setX(1080f+50f);
        }
        this.setY(242f+randY);
    }

    /*
     * Retourne un entier aléatoire entre 0 et n-1
     */
    private static int getRandom(int n){
        return (int)(Math.random()*n);
    }

    public void dispose (){
        texture.dispose();
        texture2.dispose();
    }
}
