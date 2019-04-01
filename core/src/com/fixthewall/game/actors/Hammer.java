package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.fixthewall.game.logic.GameLogic;

public class Hammer extends Actor {

    private final float STARTING_ANGLE = 20; // sprite rotation at start of the animation
    private final float ANGLE_TO_ROTATE = 45; // angle to be gradually added until the end of the animation

    private TextureRegion[] marteaux = new TextureRegion[8];
    private SequenceAction sequence;

    public Hammer(AssetManager ass) {
        initHammers(ass);
        setBounds(getX(), getY(), marteaux[0].getRegionWidth(), marteaux[0].getRegionHeight());
        setOrigin(getWidth() / 2f, getHeight() / 4f);
        setRotation(STARTING_ANGLE);
        setTouchable(Touchable.disabled); // click through
        // make the hammer invisible by default
        setColor(getColor().r, getColor().g, getColor().b, 0);

        this.sequence = new SequenceAction();
        ParallelAction parallel = new ParallelAction();
        parallel.addAction(Actions.alpha(1, 0.05f));
        parallel.addAction(Actions.rotateBy(ANGLE_TO_ROTATE, 0.15f));
        sequence.addAction(parallel);
        sequence.addAction(Actions.alpha(0, 0.1f));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, getColor().a * parentAlpha);
        int level = GameLogic.getSingleInstance().getHammerLevel();
        batch.draw(marteaux[level], getX(), getY(), getOriginX(), getOriginY(), getWidth(),
                getHeight(), getScaleX(), getScaleY(), getRotation());
        batch.setColor(color.r, color.g, color.b, parentAlpha);
    }

    public void show(float posX, float posY) {
        setX(posX - getOriginX());
        setY(posY - getOriginY());
        setRotation(STARTING_ANGLE);
        removeAction(sequence); // enlève l'action pour la reset (si on fait juste restart ça suffit pas)
        sequence.restart();
        addAction(sequence);
    }

    private void initHammers(AssetManager ass) {
        marteaux = new TextureRegion[] {
                new TextureRegion(ass.get("Marteaux/marteau_normal.png", Texture.class)),
                new TextureRegion(ass.get("Marteaux/marteau.png", Texture.class)),
                new TextureRegion(ass.get("Marteaux/marteau_Brique.png", Texture.class)),
                new TextureRegion(ass.get("Marteaux/War_hammer.png", Texture.class)),
                new TextureRegion(ass.get("Marteaux/Marteau_thor.png", Texture.class)),
                new TextureRegion(ass.get("Marteaux/marteauDiam.png", Texture.class)),
                new TextureRegion(ass.get("Marteaux/Ghal_Maraz.png", Texture.class)),
                new TextureRegion(ass.get("Marteaux/marteau_Trump.png", Texture.class))
        };
    }

}
