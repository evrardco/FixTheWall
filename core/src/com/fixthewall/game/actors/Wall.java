package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fixthewall.game.logic.GameLogic;

public class Wall extends Actor {

    // TODO voir pour potentiellement remplacer ça par une TextureRegion
    private Texture[] textures;
    private int textureIndex;
    private int textureIndexLock;
    private boolean textureLocked;

    public Wall(AssetManager ass) {
        textures = new Texture[6];
        textures[5] = ass.get("wallStates/theWall.png");
        textures[4] = ass.get("wallStates/theWall80.png");
        textures[3] = ass.get("wallStates/theWall60.png");
        textures[2] = ass.get("wallStates/theWall40.png");
        textures[1] = ass.get("wallStates/theWall20.png");
        textures[0] = ass.get("wallStates/theWallBroken.png");

        setWidth(textures[0].getWidth());
        setHeight(textures[0].getHeight());
        setBounds(getX(), getY(), getWidth(), getHeight());
        setPosition(0, 300);

        textureIndex = 0;
        textureIndexLock = 0;
        textureLocked = false;
    }

    @Override
    public void act(float delta) {
        if (textureLocked) {
            textureIndex = textureIndexLock;
        } else {
            GameLogic gameLogic = GameLogic.getSingleInstance();
            float healthRatio = (float) (gameLogic.getHealth() / gameLogic.getMaxHealth());
            textureIndex = MathUtils.ceil(healthRatio * (textures.length - 1));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textures[textureIndex], getX(), getY());
    }

    public void lockTexture(int textureIndex) {
        textureLocked = true;
        this.textureIndexLock = textureIndex;
    }

    public void unlockTexture() {
        textureLocked = false;
    }

}
