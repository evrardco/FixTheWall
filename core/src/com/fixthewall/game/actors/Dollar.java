package com.fixthewall.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fixthewall.game.Game;
import com.fixthewall.game.Helpers;
import com.fixthewall.game.actors.physics.Constants;
import com.fixthewall.game.logic.GameLogic;

public class Dollar extends Actor {

    private final AssetManager ass;
    private static final int FRAME_COLS = 4, FRAME_ROWS = 3;
    private TextureRegion[] framesDollar;
    private Animation<TextureRegion> dollarAnimation;
    private TextureRegion currentFrame;
    private float elapsedTime;

    private Rectangle bounds;
    private boolean available;
    private float deltaSpeedY;
    private float prevAccY;
    private float currentAccY;
    private float currentAccX;
    public static int visibleAmount = 0;

    private float velY;
    private float velX;

    public Dollar(final AssetManager ass) {
        this.ass = ass;
        setAnimation();

        available = GameLogic.getSingleInstance().isAccelerometerAvailable();
        reset();
    }

    public void reset(){
        this.setX(getRandom(900) + 20);
        this.setY(Game.GAME_HEIGHT + getHeight() + getRandom(200));

        bounds = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        velY = 0f;
        velX = 0f;
        setVisible(true);
        visibleAmount++;

        deltaSpeedY = 0f;
        prevAccY = 0f;
        currentAccY = 0f;
        currentAccX = 0f;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if(!isVisible()) return;

        elapsedTime += delta;
        currentFrame = dollarAnimation.getKeyFrame(elapsedTime, true);

        if (this.getY() > 150) {

            if(available) {
                prevAccY = currentAccY;
                currentAccY = Gdx.input.getAccelerometerY();
                deltaSpeedY += Math.abs(currentAccY - prevAccY);
                if (deltaSpeedY <= 1)
                    velY -= 1;
                else
                    this.velY -= deltaSpeedY;

                currentAccX = Gdx.input.getAccelerometerX();
                if (currentAccX >= 10)
                    currentAccX = 10;
                else if (currentAccX <= -10)
                    currentAccX = -10;
                this.velX -= currentAccX;

            } else {
                this.velY += Constants.GRAVITY * delta;
            }
            this.setY(this.getY() + this.velY * delta);
            this.setX(this.getX() + this.velX * delta);
        } else {
            this.setVisible(false);
            this.setX(10000.0f);
            this.setY(10000.0f);
            visibleAmount--;

        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, this.getX(), this.getY());
    }

    private void setAnimation(){
        Texture texture = ass.get("Frames/SheetFrameDollar.png");
        this.setWidth(texture.getWidth() / (float) FRAME_COLS);
        this.setHeight(texture.getHeight() / (float) FRAME_ROWS);
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);
        framesDollar = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                framesDollar[index++] = tmp[i][j];
            }
        }
        dollarAnimation = new Animation<TextureRegion>(0.08f, framesDollar);
        if(Helpers.getRandom(2) == 1){
            flip();
        }
    }

    public Rectangle getBounds() {
        return bounds.setPosition(getX(), getY());
    }

    /*
     * Retourne un entier aléatoire entre 0 et n-1
     */
    private static int getRandom(int n){
        return (int)(Math.random()*n);
    }

    public void flip(){
        for (int i = 0; i < framesDollar.length; i++)
            framesDollar[i].flip(true, false);
    }

}
