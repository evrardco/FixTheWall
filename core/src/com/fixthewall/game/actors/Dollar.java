package com.fixthewall.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fixthewall.game.Game;
import com.fixthewall.game.actors.physics.Constants;
import com.fixthewall.game.logic.GameLogic;

public class Dollar extends Actor {

    private Texture texture;
    private Rectangle bounds;
    private boolean available;
    private float deltaSpeed;
    private float prevAcc;
    private float currentAcc;
    public static int visibleAmount = 0;

    private float velY;

    public Dollar(AssetManager ass) {
        texture = ass.get("dollard.png");
        setWidth(texture.getWidth() / 3f);
        setHeight(texture.getHeight());
        available = GameLogic.getSingleInstance().isAccelerometerAvailable();
        reset();
        deltaSpeed = 0f;
        prevAcc = 0f;
        currentAcc = 0f;
    }

    public void reset(){
        this.setX(getRandom(900) + 20);
        this.setY(Game.GAME_HEIGHT + getHeight() + getRandom(200));

        bounds = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        velY = 0f;
        setVisible(true);
        visibleAmount++;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if(!isVisible()) return;

        if (this.getY() > 150) {

            if(available)
            {
                prevAcc = currentAcc;
                currentAcc = Gdx.input.getAccelerometerY();
                deltaSpeed += Math.abs(currentAcc - prevAcc);
                this.velY += Constants.GRAVITY * delta*0.001-deltaSpeed*150*delta;
            }
            else
            {
                this.velY += Constants.GRAVITY * delta*0.1;
            }
            this.setY(this.getY() + this.velY * delta);
        } else{
            this.setVisible(false);
            this.setX(10000.0f);
            this.setY(10000.0f);
            visibleAmount--;

        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY());
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

}
