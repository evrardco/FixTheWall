package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fixthewall.game.Game;
import com.fixthewall.game.actors.physics.Constants;

public class Dollar extends Actor {

    private Texture texture;
    private Rectangle bounds;

    private float velY;

    public Dollar(AssetManager ass) {
        texture = ass.get("dollard.png");
        setWidth(texture.getWidth() / 3f);
        setHeight(texture.getHeight());
        this.setX(getRandom(900) + 20);
        this.setY(Game.GAME_HEIGHT + getHeight() + getRandom(200));

        bounds = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        velY = 0f;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.getY() > 150) {
            this.velY += Constants.GRAVITY * delta;
            this.setY(this.getY() + this.velY * delta);
        } else
            this.remove();
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
