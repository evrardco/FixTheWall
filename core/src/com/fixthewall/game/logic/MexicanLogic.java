package com.fixthewall.game.logic;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.fixthewall.game.actors.Dollar;
import com.fixthewall.game.actors.Dynamite;
import com.fixthewall.game.actors.Ennemi;
import com.fixthewall.game.actors.Sun;
import com.fixthewall.game.actors.pools.EnnemiPool;
import com.fixthewall.game.upgrades.UpgradeManager;

import java.io.Serializable;

public class MexicanLogic implements Serializable {

    private transient Group ennemiGroup;
    private transient Group workerGroup;

    private double damage;
    private double heal;
    private double brickPower;
    private double mul;

    private float elapsedTime;
    private float waveNumber;
    private Sun trump;

    private int ennemiToRemove;

    public EnnemiPool pool;

    private static MexicanLogic singleInstance = null;

    public static MexicanLogic getSingleInstance() {
        if(singleInstance == null) singleInstance = new MexicanLogic();
        return singleInstance;
    }

    private MexicanLogic() {}

    public void init(double damage, double heal, double brickPower, double mul, AssetManager ass) {
        this.waveNumber = 10;
        this.damage = damage;
        this.heal = heal;
        this.brickPower = brickPower;
        this.mul = mul;
        this.trump = null;
        ennemiGroup = new Group();
        workerGroup = new Group();
        ennemiToRemove = 0;
        pool = new EnnemiPool(ass);
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getMul() {
        return mul;
    }

    public void setMul(float mul) {
        this.mul = mul;
    }

    public void doDamage() {
        GameLogic.getSingleInstance().setHealth(
                 GameLogic.getSingleInstance().getHealth() - damage * mul
        );
    }

    public void doHeal() {
        GameLogic.getSingleInstance().setHealth(
                GameLogic.getSingleInstance().getHealth() + heal
        );
        GameLogic.getSingleInstance().setBricks(
                GameLogic.getSingleInstance().getBricks() + brickPower
        );
        GameLogic.getSingleInstance().setScore(
                GameLogic.getSingleInstance().getScore() + brickPower
        );
    }

    // TODO remplacer dollarGroup par le pool
    public void updateCashRain(Group dollarGroup, AssetManager ass) {
        if (ennemiToRemove > 0) {
            for(int i = 0; i < UpgradeManager.getSingleInstance().getAllUpgrade()[2].getLevel() * 2 + 3; i++) {
                dollarGroup.addActor(new Dollar(ass));
            }
            ennemiToRemove = 0;
        }
        // Pour les performances on regarde juste les collisions si les dollar sont sur la partie
        // basse de l'écran. Environ 600 pixels comme le mur commence à 300 et les dollars
        // spawnent avec une variation en y de 200 pixel + 100 pixels pour être sûr
        if (dollarGroup.hasChildren() && dollarGroup.getChildren().get(0).getY() <= 600) {
            for (Actor dollar : dollarGroup.getChildren()) {
                for (Actor actor : ennemiGroup.getChildren()) {
                    if (actor instanceof Ennemi && ((Dollar) dollar).getBounds().overlaps((((Ennemi) actor).getBounds()))) {
                        ((Ennemi) actor).kill();
                        dollar.remove();
                    }
                }
            }
        }
    }

    public void updateDynamite(Dynamite dynamite) {
        if (dynamite.hasExploded()) {
            for (Actor actor : ennemiGroup.getChildren()) {
                if(actor instanceof Ennemi) {
                    if (dynamite.getExplosionRadius().overlaps(((Ennemi) actor).getBounds())) {
                        ((Ennemi) actor).kill();
                    }
                }
            }
        }
    }


    public void updateWave(float delta, boolean isDay, AssetManager ass) {
        elapsedTime += delta;
        // new wave every 45 seconds if day, every 25 seconds if night
        if ((elapsedTime >= 45 && isDay) || (elapsedTime >= 25 && !isDay)) {
            elapsedTime = 0f;
            waveNumber++;
            // TODO utiliser les Pools ici
            for (int i = 0; i < 1 + 2 * waveNumber; i++) {
                ennemiGroup.addActor(pool.obtain());
            }
        }
    }

    public void updateTrumpHead(Sun trump )
    {
        this.trump = trump;
    }

    public boolean checkTrumpCollision(Rectangle ennemiBounds)
    {
        if (trump.getBounds().overlaps(ennemiBounds))
        {
            return true;
        }
        return false;
    }

    /**
     * Pour sauvegarde
     */
    public void init(MexicanLogic instance){
        singleInstance = instance;
    }

    public Group getWorkerGroup() {
        return workerGroup;
    }

    public Group getEnnemiGroup() {
        return ennemiGroup;
    }

    public double getHeal() {
        return heal;
    }

    public void setHeal(double heal) {
        this.heal = heal;
    }


    public double getBrickPower() {
        return brickPower;
    }

    public void setBrickPower(double brickPower) {
        this.brickPower = brickPower;
    }

    public void setEnnemiToRemove(int num) {
        this.ennemiToRemove = num;
    }

    public int getEnnemiToRemove() {
        return ennemiToRemove;
    }
}
