package com.fixthewall.game.logic;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.fixthewall.game.actors.Dollar;
import com.fixthewall.game.actors.Dynamite;
import com.fixthewall.game.actors.Ennemi;
import com.fixthewall.game.actors.EnnemiBaleze;
import com.fixthewall.game.actors.Laser;
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

    private transient Group laserGroup;
    private transient Group brixplosionGroup;
    private transient Group nukeExplosionGroup;
    private transient Sun trump;
    private transient DollarRecycler dollarRecycler;
    public transient EnnemiPool pool;

    private transient Group dayNightCycleGroup;

    private double damage;
    private double heal;
    private double brickPower;
    private double mul;

    public Vector2 getMoonPos() {
        return moonPos;
    }

    public Vector2 getSunPos() {
        return sunPos;
    }

    public float getNightAlpha() {
        return nightAlpha;
    }

    private float elapsedTime;
    private float waveNumber;

    private double dayTime;
    private boolean isDay;
    private Vector2 moonPos;
    private Vector2 sunPos;
    private float nightAlpha;

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
        instance.laserGroup = new Group();
        instance.nukeExplosionGroup = new Group();
        instance.brixplosionGroup = new Group();
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
        laserGroup = new Group();
        nukeExplosionGroup = new Group();
        brixplosionGroup = new Group();
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
    public void setDayNightCycle(Sun sun, Moon moon, Actor background){
        this.nightAlpha = background.getColor().a;
        this.sunPos = new Vector2(sun.getX(), sun.getY());
        this.moonPos = new Vector2(moon.getX(), moon.getY());

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

    public void doBalezeDamage() {
        double balezeDamage = (GameLogic.getSingleInstance().getHealth())/10.0 + damage*100;
        GameLogic.getSingleInstance().setHealth(
                GameLogic.getSingleInstance().getHealth() - balezeDamage
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

    public void updateCashRain(Group dollarGroup) {
        if (ennemiToRemove > 0) {
            double nbreDollar = UpgradeManager.getSingleInstance().getAllUpgrade()[5].getLevel() * 1.1;
            if(nbreDollar<4) nbreDollar = 4;
            for(int i = 0; i < nbreDollar; i++) {
                Dollar dol = dollarRecycler.getOne(ass);
                if(!dollarRecycler.isRecycling())
                    dollarGroup.addActor(dol);
                dol.reset();
            }
            ennemiToRemove = 0;
        }
        // Pour les performances on regarde juste les collisions si les dollar sont sur la partie
        // basse de l'écran. Environ 600 pixels comme le mur commence à 300 + 300 pixels pour être sûr
        if(Dollar.visibleAmount == 0) return;

        if (dollarGroup.hasChildren()) {
            for (Actor dollar : dollarGroup.getChildren()) {
                if (!dollar.isVisible()) continue;
                if (dollar.getY() >= 600 || dollar.getY() <= 0) continue;
                for (Ennemi ennemi : pool.getShown()) {
                    if (((Dollar) dollar).getBounds().overlaps(((ennemi.getBounds())))) {
                            ennemi.setPayed();
                            dollar.setVisible(false);
                    }
                }
            }
        }
    }

    public void updateDynamite(Dynamite dynamite) {
        if (dynamite.hasExploded()) {
            for (Ennemi ennemi : pool.getShown()) {
                if (dynamite.getExplosionRadius().overlaps((ennemi.getBounds()))) {
                    ennemi.kill();
                }
            }
        }
    }

    public void resetWaveTime(){elapsedTime = 0f;}

    public void updateWave(float delta, boolean isDay) {

        setDay(isDay);
        if(!finishedLoading) finishLoading();
        elapsedTime += delta;
        // new wave every 45 seconds if day, every 25 seconds if night
        if ((elapsedTime >= 45f && isDay) || (elapsedTime >= 25f && !isDay)) {
            elapsedTime = 0f;
            //if(isDay) timeBetweenWavesDay *=  0.9f;
            //else timeBetweenWavesNight *=  0.7f;

            waveNumber++;
            if (waveNumber < 10) {   //PHASE 1
                for (int i = 0; i < 1 + waveNumber; i++) {
                    ennemiGroup.addActor(pool.obtain());
                }
            } else if (waveNumber < 20) { //PHASE 2
                for (int i = 0; i < 1 + waveNumber + (waveNumber-10) * 5; i++) {
                    ennemiGroup.addActor(pool.obtain());
                }
            } else if (waveNumber < 30) { //PHASE 3
                for (int i = 0; i <  waveNumber* 10; i++) {
                    ennemiGroup.addActor(pool.obtain());
                }
                for(int i = 30; i<=waveNumber; i++){
                    ennemiGroup.addActor(new EnnemiBaleze(ass));
                }
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
            float y = (float) (700 * Math.sin(dayTime/duration * Math.PI * 2) + 900);
            trump.setPosition(x, y);
        }
        else {
            trump.setVisible(false);
            moon.setVisible(true);
            this.trump = trump;
            float x = (float) (700 * Math.cos(-dayTime/duration * Math.PI * 2) + 540+ (moon.getWidth() / 2));
            float y = (float) (700 * Math.sin(-dayTime/duration * Math.PI * 2) + 900);
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

    public Group getLaserGroup() {
        return laserGroup;
    }

    public Group getNukeExplosionGroup() {
        return nukeExplosionGroup;
    }

    public Group getBrixplosionGroup() {
        return brixplosionGroup;
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
