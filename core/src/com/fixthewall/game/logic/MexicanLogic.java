package com.fixthewall.game.logic;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.fixthewall.game.actors.Dollar;
import com.fixthewall.game.actors.Dynamite;
import com.fixthewall.game.actors.Ennemi;
import com.fixthewall.game.actors.EnnemiEchelle;
import com.fixthewall.game.actors.Moon;
import com.fixthewall.game.actors.Sun;
import com.fixthewall.game.actors.pools.EnnemiPool;
import com.fixthewall.game.actors.recyclers.DollarRecycler;
import com.fixthewall.game.upgrades.UpgradeManager;

import java.io.Serializable;

public class MexicanLogic implements Serializable {

    private AssetManager ass;

    private transient Group ennemiGroup;
    private transient Group workerGroup;

    private double damage;
    private double heal;
    private double brickPower;
    private double mul;

    private float elapsedTime;
    private float waveNumber;
    private Sun trump;
    private DollarRecycler dollarRecycler;
    private double dayTime;

    private int ennemiToRemove;

    public EnnemiPool pool;

    private static MexicanLogic singleInstance = null;

    public static MexicanLogic getSingleInstance() {
        if(singleInstance == null) singleInstance = new MexicanLogic();
        return singleInstance;
    }

    private MexicanLogic() {}

    public void init(double damage, double heal, double brickPower, double mul, AssetManager ass) {
        this.waveNumber = 0;
        this.damage = damage;
        this.heal = heal;
        this.brickPower = brickPower;
        this.mul = mul;
        dayTime = 5; //TODO : changer cette valeur en la durée de la nuit/4
        this.trump = null;
        ennemiGroup = new Group();
        workerGroup = new Group();
        ennemiToRemove = 0;
        this.ass = ass;
        pool = new EnnemiPool(ass);
        dollarRecycler = new DollarRecycler(128);
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
                Dollar dol = dollarRecycler.getOne(ass);
                if(!dollarRecycler.isRecycling())
                    dollarGroup.addActor(dol);
                dol.reset();
            }
            ennemiToRemove = 0;
        }
        // Pour les performances on regarde juste les collisions si les dollar sont sur la partie
        // basse de l'écran. Environ 600 pixels comme le mur commence à 300 et les dollars
        // spawnent avec une variation en y de 200 pixel + 100 pixels pour être sûr
        if(Dollar.visibleAmount == 0) return;

        if (dollarGroup.hasChildren() && dollarGroup.getChildren().get(0).getY() <= 600) {
            for (Actor dollar : dollarGroup.getChildren()) {
                if(!dollar.isVisible()) continue;
                for (Actor actor : ennemiGroup.getChildren()) {
                    if (actor instanceof Ennemi
                            && actor.isVisible()
                            && ((Dollar) dollar).getBounds().overlaps((((Ennemi) actor).getBounds()))) {

                            ((Ennemi) actor).kill();
                            dollar.setVisible(false);
                    }
                }
            }
        }
    }

    public void updateDynamite(Dynamite dynamite) {
        if (dynamite.hasExploded()) {
            for (Actor actor : ennemiGroup.getChildren()) {
                if(actor instanceof Ennemi) {
                    if (actor.isVisible() && dynamite.getExplosionRadius().overlaps(((Ennemi) actor).getBounds())) {
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

    public void updateTrumpHead(Sun trump, Moon moon, float delta, float duration) {

        dayTime = dayTime + delta;
        if (dayTime > duration)
        {
            dayTime = 0f;
        }
        if (dayTime < duration/2 ) {
            trump.setVisible(true);
            moon.setVisible(false);
            this.trump = trump;
            float x = (float) (700 * Math.cos(dayTime/duration * Math.PI * 2) + 540 - (moon.getWidth() / 2));
            float y = (float) (700 * Math.sin(dayTime/duration * Math.PI * 2) + 960);  //Playing with alpha is a bad idea
            trump.setX(x);
            trump.setY(y);
        }
        else {
            trump.setVisible(false);
            moon.setVisible(true);
            this.trump = trump;
            float x = (float) (700 * Math.cos(-dayTime/duration * Math.PI * 2) + 540 - (moon.getWidth() / 2));
            float y = (float) (700 * Math.sin(-dayTime/duration * Math.PI * 2) + 960);
            moon.setX(x);
            moon.setY(y);
        }
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
