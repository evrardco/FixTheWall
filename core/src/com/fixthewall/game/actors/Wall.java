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
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        GameLogic gameLogic = GameLogic.getSingleInstance();

        float healthRatio = (float) (gameLogic.getHealth() / gameLogic.getMaxHealth());
        int index = MathUtils.ceil(healthRatio * (textures.length - 1));

        batch.draw(textures[index], getX(), getY());
    }

}
