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
* Ennemis avance entre Y = [250; 298] (intervale de 48 pixels)
*  et s'arrêt dans l'intervale X = [0; 1000] (intervale de 1000 pixels)
* */

public class Ennemi extends Actor {

    private float power;
    private int level;
    private boolean fromLeft;
    //variables d'animation:
    private static final int FRAME_COLS = 4, FRAME_ROWS = 5;
    private Animation<TextureRegion> ennemiAnimation;
    private Texture texture;
    // Variable for tracking elapsed time for the animation
    private float elapsedTime;


    public Ennemi (int level){
        if (level < 1)
            this.level = 1;
        else
            this.level = level;
        this.setPower();
        this.setSide();
        //Set animation
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
        ennemiAnimation = new Animation<TextureRegion>(1f/(duration*4), ennemiFrames);
        //
        setTouchable(Touchable.disabled); // clik through
        this.setCoor();
        float randX = (float)getRandom(1000);

        this.addAction(new Actions().moveTo(randX, this.getY(), duration));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw( ennemiAnimation.getKeyFrame(elapsedTime, true), this.getX(), this.getY());
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
        float randY = (float)getRandom(49);
        if(fromLeft){
            this.setX(-(96f+100f));
        }
        else{
            this.setX(1080f+100f);
        }
        this.setY(250f+randY);
    }

    /*
     * Retourne un entier entre 0 et n-1
     */
    private static int getRandom(int n){
        int size = (int)(Math.random()*n);
        if(size == n)size -= 1;
        return size;
    }

    public void dispose (){
        texture.dispose();
    }
}
