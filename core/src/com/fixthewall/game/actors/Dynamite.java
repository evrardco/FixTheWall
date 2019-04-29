package com.fixthewall.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fixthewall.game.actors.anim.Brixplosion;
import com.fixthewall.game.actors.anim.Explosion;
import com.fixthewall.game.actors.physics.Constants;
import com.fixthewall.game.logic.GameLogic;

public class Dynamite extends Actor{

    private static final float DETONATION_TIME = 3f;

    public static boolean onPause;
    private final AssetManager ass;
    private boolean visible;
    private Animation<TextureRegion> dynamiteAnimation;
    // Variable for tracking elapsed time for the animation
    private float elapsedTime;
    private float countdown;
    private TextureRegion currentFrame;
    private boolean isFalling;
    private boolean isExploding;
    private Rectangle bounds;
    private Label countdownLabel;

    // moche mais le setColor du Label est buggué et affiche du noir si on l'utilise
    private Label.LabelStyle redStyle;
    private Label.LabelStyle yellowStyle;
    private Label.LabelStyle greenStyle;

    private float velY;

    public Dynamite (AssetManager ass) {
        Texture texture = ass.get("Frames/SheetFrameDynamite.png");
        onPause = false;
        isFalling = false;
        isExploding = false;
        this.ass = ass;
        //set size actor
        setWidth(texture.getWidth()/3f);
        setHeight(texture.getHeight());
        visible = false;
        setTouchable(Touchable.disabled);

        redStyle = new Label.LabelStyle(ass.get("PoetsenOne30.ttf", BitmapFont.class), Color.RED);
        yellowStyle = new Label.LabelStyle(ass.get("PoetsenOne30.ttf", BitmapFont.class), Color.YELLOW);
        greenStyle = new Label.LabelStyle(ass.get("PoetsenOne30.ttf", BitmapFont.class), Color.GREEN);
        countdownLabel = new Label("" + (int) Math.ceil(DETONATION_TIME), greenStyle);
        countdownLabel.setPosition(getX() + getWidth() / 2f - countdownLabel.getWidth() / 2f, getY() - countdownLabel.getHeight() / 2f);

        //Set animation
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth()/3,
                texture.getHeight());
        TextureRegion[] DynamiteFrames = new TextureRegion[3];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                DynamiteFrames[index++] = tmp[i][j];
            }
        }
        dynamiteAnimation = new Animation<TextureRegion>(0.1f, DynamiteFrames);
        //
        elapsedTime = 0f;
        countdown = DETONATION_TIME;

        bounds = new Rectangle(this.getX() - 50, this.getY(), this.getWidth() + 100, this.getHeight());

        velY = 0f;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (!isFalling) {

            if (visible) {
                countdown -= delta;
                int temp = (int) Math.ceil(countdown);
                countdownLabel.setText("" + temp);
                if (temp == 3)
                    countdownLabel.setStyle(greenStyle);
                else if (temp == 2)
                    countdownLabel.setStyle(yellowStyle);
                else
                    countdownLabel.setStyle(redStyle);
            }
            if (countdown <= 0) {
                GameLogic.getSingleInstance().reduceHealth(GameLogic.getSingleInstance().getMaxHealth() * 0.25);
                this.explode();
            }
        } else {
            velY += Constants.GRAVITY * delta;
            this.setY(this.getY() + velY * delta);
            countdownLabel.setPosition(getX() + getWidth() / 2f - countdownLabel.getWidth() / 2f, getY() - countdownLabel.getHeight() / 2f);
            if (this.getY() < 300) {
                this.explode();
            }
        }

        if (!visible && getRandom(300) == 10) {
            visible = true;
            setTouchable(Touchable.enabled);
            this.setPosition(getRandom(1021), 300+getRandom(461));
            countdownLabel.setPosition(getX() + getWidth() / 2f - countdownLabel.getWidth() / 2f, getY() - countdownLabel.getHeight() / 2f);
            velY = 0f;
        }

        if(visible) {
            elapsedTime += delta;
            currentFrame = dynamiteAnimation.getKeyFrame(elapsedTime, true);
        }
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(visible) {
            batch.draw(currentFrame, this.getX(), this.getY());
            if (!isFalling)
                countdownLabel.draw(batch, parentAlpha);
        }
    }

    public ClickListener getListener() {
        return  new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                if (!onPause) {
                    isFalling = true;
                }
            }
        };

    }

    public Rectangle getExplosionRadius() {
        return bounds.setPosition(getX() - 50, getY());
    }

    public void explode() {
        isFalling = false;
        isExploding = true;
        //Brixplosion brixplosion = new Brixplosion(15, ass, this.getX(), this.getY(), 500.0f);
        Explosion explosion = new Explosion(this.getX()-82f, this.getY()-48f, ass);// les "-" pour centrer l'explosion.
        //this.getParent().addActor(brixplosion);
        this.getParent().addActor(explosion);
        explosion.explode();
        visible = false;
        setTouchable(Touchable.disabled);
        countdown = DETONATION_TIME;
    }

    public void setExploding(boolean val)
    {
        isExploding = false;
    }

    public boolean hasExploded() {
        boolean temp = isExploding;
        setExploding(false);
        return temp;
    }

    /*
     * Retourne un entier aléatoire entre 0 et n-1
     */
    private static int getRandom(int n){
        return (int)(Math.random()*n);
    }

}
