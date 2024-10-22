package com.fixthewall.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.fixthewall.game.actors.physics.Constants;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.logic.MexicanLogic;
import com.fixthewall.game.upgrades.AbstractUpgrade;
import com.fixthewall.game.upgrades.UpgradeManager;


public class Nuke extends Actor {
    private float speed;
    private Image ascendingImage;
    private Image descendingImage;
    private float timeBeforeDescending;
    private AssetManager ass;
    private Image blinder;
    private Image flame;
    private boolean exploded;

    public Nuke(AssetManager ass){
        TextureRegion flippedUp = new TextureRegion(ass.get("nuke/nuke.png", Texture.class));
        flippedUp.flip(false, true);
        ascendingImage = new Image((flippedUp));
        descendingImage = new Image(ass.get("nuke/nuke.png", Texture.class));
        ascendingImage.setScale(0.15f);

        this.ass = ass;
        this.setPosition(Gdx.graphics.getWidth()/2f, Constants.GROUND_LEVEL);
        ascendingImage.setPosition(this.getX(), this.getY());
        descendingImage.setPosition(this.getX(), this.getY());

        blinder = new Image(ass.get("fx/blinding_foreground.png", Texture.class));
        flame = new Image(ass.get("fx/fusion.png", Texture.class));
        blinder.setTouchable(Touchable.disabled);
        flame.setTouchable(Touchable.disabled);
        blinder.setVisible(false);
        flame.setVisible(false);
        MexicanLogic.getSingleInstance().getNukeExplosionGroup().addActor(blinder);
        MexicanLogic.getSingleInstance().getNukeExplosionGroup().addActor(flame);

    }

    public void launch(){
        this.getParent().addActor(ascendingImage);

        this.speed = 200f;
        this.timeBeforeDescending = 5f;
        this.exploded = false;

    }

    @Override
    public void act(float delta){
        if(GameLogic.getSingleInstance().isMenuUpgrade()) return;
        if(exploded){

            Color f = flame.getColor();
            Color b = blinder.getColor();

            if(b.a > 0f){
                b.a -= delta*0.05f;
                blinder.setColor(b);
            } else blinder.remove();

            if(f.a > 0f){
                f.a -= delta*0.025f;
                flame.setColor(f);
            }else flame.remove();

            if(f.a <= 0 && b.a <= 0) this.remove();

            return;
        }
        this.setPosition(
                this.getX(),
                this.getY()+speed*delta
        );

        if(this.getY() > Gdx.graphics.getHeight() + ascendingImage.getHeight()){
            this.speed = 0.0f;
            timeBeforeDescending -= delta;
        }

        if(timeBeforeDescending <= 0.0f){
            this.speed = -1600f;
            this.ascendingImage.remove();
            MexicanLogic.getSingleInstance().getEnnemiGroup().addActor(descendingImage);



        }

        if(this.getY() < Constants.GROUND_LEVEL - descendingImage.getHeight()){
            this.explode();
        }
        ascendingImage.setPosition(this.getX(), this.getY());
        descendingImage.setPosition(this.getX(), this.getY());

    }

    public void explode(){/*Explosion exp = new Explosion(this.getX(), this.getY(), ass);
        this.getParent().addActor(exp);*/

        flame.setScale(2f);
        flame.setPosition(
                this.getX() + descendingImage.getWidth()/2f - flame.getWidth(),
                   this.getY() + descendingImage.getHeight()/2f- flame.getHeight()
        );
        flame.setVisible(true);
        blinder.setVisible(true);

        this.setVisible(false);
        this.setY(Constants.GROUND_LEVEL);
        this.speed = 0.0f;
        this.exploded = true;
        Group workerGroup = MexicanLogic.getSingleInstance().getWorkerGroup();
        Group ennemiGroup = MexicanLogic.getSingleInstance().getEnnemiGroup();

        ennemiGroup.clearChildren();
        workerGroup.clearChildren();
        MexicanLogic.getSingleInstance().getEnnemiPool().clear();
        MexicanLogic.getSingleInstance().getEnnemiBalezePool().clear();
        MexicanLogic.getSingleInstance().getWorkerPool().clear();
        MexicanLogic.getSingleInstance().resetWaveTime();
        //Reset GameLogic's stat
        GameLogic.getSingleInstance().nukeReset();
        //Reset Upgrades
        resetUpgrades();

    }

    private void resetUpgrades(){
        AbstractUpgrade[] allUpgrade = UpgradeManager.getSingleInstance().getAllUpgrade();
        allUpgrade[UpgradeManager.WALL_LIFE].reset(0 , 500);
        allUpgrade[UpgradeManager.WALL_WORKER].reset(0, 200);
        allUpgrade[UpgradeManager.WALL_WORKER_LEVEL].reset(0, 500);
        UpgradeManager.getSingleInstance().update();
    }



}
