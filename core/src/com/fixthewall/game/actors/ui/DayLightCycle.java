package com.fixthewall.game.actors.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;



public class DayLightCycle extends Group {
    private Image sun;

    public DayLightCycle(float x, float y, AssetManager ass) {
        super();
        sun = new Image(ass.get("sun", Texture.class));


    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
