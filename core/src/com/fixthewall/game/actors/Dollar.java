package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Dollar extends Actor {

    private Texture texture;

    public Dollar(AssetManager ass) {
        texture = ass.get("dollard.png");
        this.setX(getRandom(900)+20);
        this.setY(1300+getRandom(100));
        setWidth(texture.getWidth()/3f);
        setHeight(texture.getHeight());
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.getY() > 150) {
            this.setY(this.getY()-delta*200); //5 sec --> -1500
        }
        else {
            this.remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY());
    }

    /*
     * Retourne un entier aléatoire entre 0 et n-1
     */
    private static int getRandom(int n){
        return (int)(Math.random()*n);
    }

}
