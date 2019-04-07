package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TeteTrump extends Actor {
    private final AssetManager ass;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> sunAnimation;
    private static final int FRAME_COLS = 4, FRAME_ROWS = 4;
    private static final int FRAME_COLS2 = 4, FRAME_ROWS2 = 2;
    private float elapsedTime;
    private TextureRegion[] sunFrames;

    public TeteTrump(AssetManager ass){
        this.ass = ass;

        this.setX(365f);
        this.setY(1500f);
        Texture texture = ass.get("Frames/SheetFrameSun.png");

        this.setWidth(texture.getWidth() / (float) FRAME_COLS);
        this.setHeight(texture.getHeight() / (float) FRAME_ROWS);

        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);

        sunFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                sunFrames[index++] = tmp[i][j];
            }
        }
        sunAnimation = new Animation<TextureRegion>(0.07f, sunFrames);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
        currentFrame = sunAnimation.getKeyFrame(elapsedTime, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, this.getX(), this.getY());
    }

}
