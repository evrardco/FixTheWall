package com.fixthewall.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fixthewall.game.logic.GameLogic;

public class Wall extends Actor {

    // TODO voir pour potentiellement remplacer ça par une TextureRegion
    private Texture[] textures;

    public Wall() {
        textures = new Texture[6];
        textures[5] = new Texture("wallStates/theWall.png");
        textures[4] = new Texture("wallStates/theWall80.png");
        textures[3] = new Texture("wallStates/theWall60.png");
        textures[2] = new Texture("wallStates/theWall40.png");
        textures[1] = new Texture("wallStates/theWall20.png");
        textures[0] = new Texture("wallStates/theWallBroken.png");

        setWidth(textures[0].getWidth());
        setHeight(textures[0].getHeight());
        setBounds(0, 0, getWidth(), getHeight());
        setPosition(0, 300);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        GameLogic gameLogic = GameLogic.getSingleInstance();

        float healthRatio = (float) (gameLogic.getHealth() / gameLogic.getMaxHealth());
        int index = MathUtils.ceil(healthRatio * (textures.length - 1));

        batch.draw(textures[index], getX(), getY());
    }

    public void dispose() {
        for (Texture texture : textures)
            texture.dispose();
    }

}
