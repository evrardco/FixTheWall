package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.fixthewall.game.logic.MexicanLogic;

public class Laser extends Actor {

    private Texture texture;
    private Rectangle bounds;

    public Laser(AssetManager ass, float x, float y, float targetX, float targetY) {
        texture = ass.get("laser.png");
        setPosition(x, y);
        bounds = new Rectangle(this.getX(), this.getY(), 10, 10);

        addAction(Actions.sequence(
                Actions.moveTo(targetX, targetY, 1f),
                Actions.removeActor()
                )
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getY() >= 600 || getY() <= 0) return;
        for (Ennemi ennemi : MexicanLogic.getSingleInstance().getPool().getShown()) {
            if (ennemi.getBounds().overlaps(getBounds())) {
                ennemi.kill();
            }
        }
    }

    public Rectangle getBounds() {
        return bounds.setPosition(getX(), getY());
    }
}
