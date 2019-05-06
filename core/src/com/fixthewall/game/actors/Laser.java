package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.logic.MexicanLogic;

public class Laser extends Actor {

    private TextureRegion texture;
    private Rectangle bounds;

    public Laser(AssetManager ass, float x, float y, float targetX, float targetY) {
        texture = new TextureRegion(ass.get("laser.png", Texture.class));

        setPosition(x, y);
        setBounds(getX(), getY(), texture.getRegionWidth(), texture.getRegionHeight());
        bounds = new Rectangle(this.getX(), this.getY(), 10, 10);
        setRotation(-90);

        addAction(Actions.sequence(
                Actions.moveTo(targetX, targetY, 1f),
                Actions.removeActor()
                )
        );

        Vector2 pos = new Vector2(getX(), getY());
        Vector2 targetPos = new Vector2(targetX, targetY);
        Vector2 targetPosRel = targetPos.cpy().sub(pos);

        rotateBy(targetPosRel.angle());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(),
                getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        if(GameLogic.getSingleInstance().isMenuUpgrade()) return;
        super.act(delta);
        if (getY() >= 600 || getY() <= 0) return;
        for (Ennemi ennemi : MexicanLogic.getSingleInstance().getEnnemiPool().getShown()) {
            if (ennemi.getBounds().overlaps(getBounds())) {
                ennemi.kill(true);
                remove();
            }
        }
    }

    public Rectangle getBounds() {
        return bounds.setPosition(getX() + getWidth() / 2f, getY() - getHeight());
    }
}
