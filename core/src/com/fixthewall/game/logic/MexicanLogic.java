package com.fixthewall.game.logic;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.fixthewall.game.actors.Dollar;
import com.fixthewall.game.actors.Dynamite;
import com.fixthewall.game.actors.Ennemi;
import com.fixthewall.game.actors.Moon;
import com.fixthewall.game.actors.Nuke;
import com.fixthewall.game.actors.Sun;
import com.fixthewall.game.actors.Worker;
import com.fixthewall.game.actors.pools.EnnemiPool;
import com.fixthewall.game.actors.recyclers.DollarRecycler;
import com.fixthewall.game.upgrades.UpgradeManager;

import java.io.Serializable;

public class MexicanLogic implements Serializable {

    private transient AssetManager ass;

    private transient Group ennemiGroup;
    private transient Group workerGroup;
    private transient Sun trump;
    private transient DollarRecycler dollarRecycler;
    public transient EnnemiPool pool;



    private transient Group dayNightCycleGroup;



    private double damage;
    private double heal;
    private double brickPower;
    private double mul;

    private float elapsedTime;
    private float waveNumber;

    private double dayTime;
    private boolean isDay;

    public int getEnnemiCount() {
        return ennemiCount;
    }

    public void setEnnemiCount(int ennemiCount) {
        this.ennemiCount = ennemiCount;
    }

    private int ennemiToRemove;
    private int ennemiCount;


    private static MexicanLogic singleInstance = null;
    private boolean finishedLoading;

    private float timeBetweenWavesDay;
    private float timeBetweenWavesNight;

    public static MexicanLogic getSingleInstance() {
        if(singleInstance == null) singleInstance = new MexicanLogic();
        return singleInstance;
    }

    private MexicanLogic() {}

    public EnnemiPool getPool() {
        return pool;
    }

    /**
     * Pour sauvegarde
     */
    public static void init(MexicanLogic instance, AssetManager ass){
        singleInstance = instance;
        instance.trump = null;
        instance.ennemiGroup = new Group();
        instance.workerGroup = new Group();
        instance.dayNightCycleGroup = new Group();

        instance.ennemiToRemove = 0;
        instance.ass = ass;
        instance.pool = new EnnemiPool(ass);
        instance.dollarRecycler = new DollarRecycler(128);
        instance.finishedLoading = false;

    }

    private void finishLoading(){

        int numWorker = UpgradeManager.getSingleInstance().getAllUpgrade()[3].getLevel();
        for(int i = 0; i < numWorker; i++){
            addWorker();
        }
        for(int i = 0; i < ennemiCount; i++ ){
            ennemiGroup.addActor(pool.obtain());
        }
        this.finishedLoading = true;

    }

    public void init(double damage, double heal, double brickPower, double mul, AssetManager ass) {

        this.waveNumber = 0;
        this.damage = damage;
        this.heal = heal;
        this.brickPower = brickPower;
        this.mul = mul;
        dayTime = 300f/4; //TODO : changer cette valeur en la durée de la nuit/4
        isDay = true;
        this.trump = null;
        ennemiGroup = new Group();
        workerGroup = new Group();
        dayNightCycleGroup = new Group();

        ennemiToRemove = 0;
        this.ass = ass;
        pool = new EnnemiPool(ass);
        dollarRecycler = new DollarRecycler(128);
        this.ennemiCount = 0;
        this.finishedLoading = true;
        this.timeBetweenWavesDay = 45f;
        this.timeBetweenWavesDay = 25f;
    }

    public void launchNuke(){
        Nuke nuke = new Nuke(ass);
        this.dayNightCycleGroup.addActor(nuke);
        nuke.launch();
    }
    public Group getDayNightCycleGroup() {
        return dayNightCycleGroup;
    }

    public void setDayNightCycleGroup(Group dayNightCycleGroup) {
        this.dayNightCycleGroup = dayNightCycleGroup;
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

    public void addWorker() {
        this.workerGroup.addActor(new Worker(ass));
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

                            //((Ennemi) actor).kill();
                            ((Ennemi) actor).setPayed();
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

    public void resetWaveTime(){elapsedTime = 0f;}

    public void updateWave(float delta, boolean isDay, AssetManager ass) {

        setDay(isDay);
        if(!finishedLoading) finishLoading();
        elapsedTime += delta;
        // new wave every 45 seconds if day, every 25 seconds if night
        if ((elapsedTime >= 45f && isDay) || (elapsedTime >= 25f && !isDay)) {
            elapsedTime = 0f;
            //if(isDay) timeBetweenWavesDay *=  0.9f;
            //else timeBetweenWavesNight *=  0.7f;

            waveNumber++;
            // TODO utiliser les Pools ici
            for (int i = 0; i < 1 + 2 * waveNumber; i++) {
                ennemiGroup.addActor(pool.obtain());
            }
        }
    }

    public void updateTrumpHead(Sun trump, Moon moon, float delta, float duration) {

        dayTime = dayTime + delta;
        if (dayTime > duration) {
            dayTime = 0f;
        }
        if (dayTime < duration/2 ) {
            trump.setVisible(true);
            moon.setVisible(false);
            this.trump = trump;
            float x = (float) (700 * Math.cos(dayTime/duration * Math.PI * 2) + 540 - (moon.getWidth() / 2));
            float y = (float) (700 * Math.sin(dayTime/duration * Math.PI * 2) + 830);
            trump.setPosition(x, y);
        }
        else {
            trump.setVisible(false);
            moon.setVisible(true);
            this.trump = trump;
            float x = (float) (700 * Math.cos(-dayTime/duration * Math.PI * 2) + 540 - (moon.getWidth() / 2));
            float y = (float) (700 * Math.sin(-dayTime/duration * Math.PI * 2) + 830);
            moon.setPosition(1080-x, y);
        }
    }

    public boolean checkTrumpCollision(Rectangle ennemiBounds) {
        return trump.getBounds().overlaps(ennemiBounds);
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

    public void setDay(boolean value) {isDay = value;}

    public boolean isDay() {return isDay;}


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
