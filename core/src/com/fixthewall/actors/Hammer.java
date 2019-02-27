package com.fixthewall.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Hammer extends Actor {

    // TODO disparition en fondu à la fin de la rotation + effet particules

    private final float ANIMATION_DURATION = 0.15f; // in seconds
    private final float STARTING_ANGLE = 20; // sprite rotation at start of the animation
    private final float ANGLE_TO_ROTATE = 45; // angle to be gradually added until the end of the animation

    private Sprite sprite;
    private int power;
    private int level;
    private boolean show;
    private float posX, posY;
    private float showTime; // how much of time has it been shown

    public Hammer(int level) {
        if (level < 1)
            this.level = 1;
        else
            this.level = level;
        this.setPower();
        this.sprite = new Sprite(new Texture("marteau.png"));
        this.sprite.setOrigin(this.sprite.getOriginX(), 50);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        this.sprite.setRotation(STARTING_ANGLE);
        this.show = false;
        this.posX = 0;
        this.posY = 0;
        this.showTime = 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float frameTime = ANIMATION_DURATION * (1 / delta);
        float degreesToRotate = ANGLE_TO_ROTATE / frameTime;

        if (this.show) {
            this.showTime += delta;
            if (this.showTime >= ANIMATION_DURATION) {
                this.show = false;
                this.sprite.setRotation(STARTING_ANGLE);
                this.showTime = 0;
            } else {
                this.sprite.setX(this.posX);
                this.sprite.setY(this.posY);
                this.sprite.rotate(degreesToRotate);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.show)
            sprite.draw(batch);
    }

    public void show(float posX, float posY) {
        if (this.show) {
            this.showTime = 0;
            this.sprite.setRotation(STARTING_ANGLE);
        }
        this.posX = posX - this.sprite.getOriginX();
        this.posY = posY - this.sprite.getOriginY();
        this.show = true;
    }

    public void hide() {
        this.show = false;
        this.showTime = 0;
        this.sprite.setRotation(STARTING_ANGLE);
    }

    public int getPower() {
        return this.power;
    }

    private void setPower() {
        // TODO equilibrage de ça
        this.power = 2 * this.level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.setPower();
    }

    public void dispose() {
        this.sprite.getTexture().dispose();
    }
}
