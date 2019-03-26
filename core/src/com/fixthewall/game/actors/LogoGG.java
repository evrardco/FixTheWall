package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LogoGG extends Actor {

    private Texture background;
    private Texture brick;
    private float progress;

    public LogoGG(AssetManager ass) {
        progress = 0;
        background = ass.get("logoGG/logoGG_background.png", Texture.class);
        brick = ass.get("logoGG/logoGG_brick.png", Texture.class);
        setHeight(background.getHeight());
        setWidth(background.getWidth());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background, getX(), getY());

        // 12 is the number of bricks total
        int numBricks = MathUtils.ceil(progress * 12);
        for (int i = 0, j = 0, count = 0; count < numBricks; count++, i++) {
            // each 4 bricks we move up
            if (i != 0 && i % 4 == 0) {
                j++;
                i = 0;
            }

            // 84 is the arbitrary offset that makes a 4 pixels spacing between bricks
            int offsetx = 84 * i;
            int offsety = 84 * j;

            batch.draw(brick, getX() + offsetx, getY() + offsety);
        }
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

}
