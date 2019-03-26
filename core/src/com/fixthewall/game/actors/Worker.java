package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fixthewall.game.Game;
import com.fixthewall.game.Helpers;
import com.fixthewall.game.logic.MexicanLogic;

public class Worker extends Actor {

    private static final int FRAME_COLS = 4, FRAME_ROWS = 2;
    private static final float WALL_TOP = 600 + 300 - 7;

    private Animation<TextureRegion> animation;
    private TextureRegion hitFrame;
    private TextureRegion currentFrame;
    private TextureRegion previousFrame;
    private final float hitSpeed = 0.1f;
    private float elapsedTime;

    public Worker(AssetManager ass) {
        Texture texture = ass.get("Frames/SheetFrameEnnemiHit.png");
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        boolean flip = (int) Helpers.getRandom(2) == 1;
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                if (flip)
                    tmp[i][j].flip(true, false);
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(hitSpeed, frames);
        hitFrame = frames[3]; //Frame de frappe
        elapsedTime = 0;

        setPosition((float) Helpers.getRandom((int) (Game.GAME_WIDTH - tmp[0][0].getRegionWidth())), WALL_TOP);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
        currentFrame = animation.getKeyFrame(elapsedTime, true);
        if (currentFrame == hitFrame && (previousFrame == null || previousFrame != currentFrame))
            this.fixTheWall();
        previousFrame = currentFrame;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, getX(), getY());
    }

    public void fixTheWall() {
        MexicanLogic.getSingleInstance().doHeal();
    }
}
