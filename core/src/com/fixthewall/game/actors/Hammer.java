package com.fixthewall.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Hammer extends Actor {

    private final float STARTING_ANGLE = 20; // sprite rotation at start of the animation
    private final float ANGLE_TO_ROTATE = 45; // angle to be gradually added until the end of the animation

    private TextureRegion texture;
    private int bricksPower; // amount of bricks the hammer generate
    private int healPower; // amount of life the hammer heals the wall
    private int level;
    private boolean show;
    private float posX, posY;

    private SequenceAction sequence;

    public Hammer(int level) {
        if (level < 1)
            this.level = 1;
        else
            this.level = level;
        this.setPowers();
        this.texture = new TextureRegion(new Texture("marteau.png"));
        setBounds(getX(),getY(),texture.getRegionWidth(),texture.getRegionHeight());
        setOrigin(this.texture.getRegionWidth() / 2f, this.texture.getRegionHeight() / 4f);
        addAction(Actions.rotateBy(10f));
        setRotation(STARTING_ANGLE);
        setTouchable(Touchable.disabled); // clik through
        this.show = false;
        this.posX = 0;
        this.posY = 0;

        this.sequence = new SequenceAction();
        ParallelAction parallel = new ParallelAction();
        parallel.addAction(new Actions().alpha(1, 0.05f));
        parallel.addAction(new Actions().rotateBy(ANGLE_TO_ROTATE, 0.15f));
        sequence.addAction(parallel);
        sequence.addAction(new Actions().alpha(0, 0.1f));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (this.show) {
            setX(this.posX);
            setY(this.posY);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.show) {
            Color color = batch.getColor();
            batch.setColor(color.r, color.g, color.b, getColor().a);
            batch.draw(this.texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(),
                    getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    public void show(float posX, float posY) {
        this.posX = posX - getOriginX();
        this.posY = posY - getOriginY();
        removeAction(sequence); // enlève l'action pour la reset (si on fait juste restart ça suffit pas)
        sequence.restart();
        addAction(sequence);
        setRotation(STARTING_ANGLE);
        this.show = true;
    }

    public int getHealPower() {
        return this.healPower;
    }

    public int getBricksPower() {
        return this.bricksPower;
    }

    private void setPowers() {
        // TODO equilibrage de ça
        this.bricksPower = 2 * this.level;
        this.healPower = 2 * this.level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.setPowers();
    }

    public void dispose() {

        this.texture.getTexture().dispose();
    }
}
