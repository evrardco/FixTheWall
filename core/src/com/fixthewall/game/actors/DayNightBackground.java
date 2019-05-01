package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fixthewall.game.logic.GameLogic;

public class DayNightBackground extends Actor {

    private Texture dayTexture;
    private Texture nightTexture;

    public DayNightBackground(AssetManager ass) {
        dayTexture = ass.get("fondWall.png");
        nightTexture = ass.get("fondWall-nuit.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, parentAlpha);
        batch.draw(dayTexture, getX(), getY());

        batch.setColor(color.r, color.g, color.b, getColor().a * parentAlpha);
        batch.draw(nightTexture, getX(), getY());
        batch.setColor(color.r, color.g, color.b, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        double dayNightTime = GameLogic.getSingleInstance().getDayNightTime();
        float alpha = 0;
        if (dayNightTime >= 0 && dayNightTime <= GameLogic.DAY_NIGHT_CYCLE_LEN / 4f) { // (alpha: 0.5 -> 0) 0 < time < 75
            alpha = 0.5f;
            alpha -= (dayNightTime / ((float) GameLogic.DAY_NIGHT_CYCLE_LEN / 4f)) * 0.5f;
        } else if (dayNightTime > GameLogic.DAY_NIGHT_CYCLE_LEN / 4f && dayNightTime <= GameLogic.DAY_NIGHT_CYCLE_LEN / 2f) { // (alpha: 0 -> 0.5) 75 < time < 150
            alpha = (float) (dayNightTime / ((float) GameLogic.DAY_NIGHT_CYCLE_LEN / 2f)) - 0.5f;
        } else if (dayNightTime > GameLogic.DAY_NIGHT_CYCLE_LEN / 2f && dayNightTime <= GameLogic.DAY_NIGHT_CYCLE_LEN * 0.75f) { // (alpha: 0.5 -> 1) 150 < time < 225
            alpha = (float) (dayNightTime / ((float) GameLogic.DAY_NIGHT_CYCLE_LEN / 2f)) - 0.5f;
        } else if (dayNightTime > GameLogic.DAY_NIGHT_CYCLE_LEN * 0.75f && dayNightTime <= GameLogic.DAY_NIGHT_CYCLE_LEN) { // (alpha: 1 -> 0.5) 225 < time < 300
            alpha = 2.0f;
            alpha -= (dayNightTime / ((float) GameLogic.DAY_NIGHT_CYCLE_LEN / 4f)) * 0.5f;
            alpha += 0.5f;
        }
        setColor(getColor().r, getColor().g, getColor().b, alpha);
    }
}
