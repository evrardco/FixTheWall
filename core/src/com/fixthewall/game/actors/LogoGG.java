package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LogoGG extends Actor {

    private Texture[] textures;
    private float progress;

    public LogoGG(AssetManager ass) {
        progress = 0;
        textures = new Texture[] {
                ass.get("logoGG/logoGG_0.png", Texture.class),
                ass.get("logoGG/logoGG_1.png", Texture.class),
                ass.get("logoGG/logoGG_2.png", Texture.class),
                ass.get("logoGG/logoGG_3.png", Texture.class),
                ass.get("logoGG/logoGG_4.png", Texture.class),
                ass.get("logoGG/logoGG_5.png", Texture.class),
                ass.get("logoGG/logoGG_6.png", Texture.class),
                ass.get("logoGG/logoGG_7.png", Texture.class),
                ass.get("logoGG/logoGG_8.png", Texture.class),
                ass.get("logoGG/logoGG_9.png", Texture.class),
                ass.get("logoGG/logoGG_10.png", Texture.class),
                ass.get("logoGG/logoGG_11.png", Texture.class),
                ass.get("logoGG/logoGG_12.png", Texture.class)
        };
        setHeight(textures[0].getHeight());
        setWidth(textures[0].getWidth());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        int index = MathUtils.ceil(progress * (textures.length - 1));

        batch.draw(textures[index], getX(), getY());
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

}
