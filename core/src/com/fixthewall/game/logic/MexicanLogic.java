package com.fixthewall.game.logic;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.fixthewall.game.actors.Dollar;
import com.fixthewall.game.actors.Dynamite;
import com.fixthewall.game.actors.Ennemi;
import com.fixthewall.game.actors.EnnemiBaleze;
import com.fixthewall.game.actors.Moon;
import com.fixthewall.game.actors.Nuke;
import com.fixthewall.game.actors.Sun;
import com.fixthewall.game.actors.pools.EnnemiBalezePool;
import com.fixthewall.game.actors.pools.EnnemiPool;
import com.fixthewall.game.actors.pools.WorkerPool;
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
    private transient EnnemiPool ennemiPool;
    private transient EnnemiBalezePool ennemiBalezePool;
    private transient WorkerPool workerPool;
    private transient boolean disabledNPCs;

    private transient Group dayNightCycleGroup;

    private double damage;
    private double heal;
    private double brickPower;
    private double mul;

    private float elapsedTime;
    private float waveNumber;
    private float bestWaveNumber;

    private int ennemiToRemove;
    private int ennemiBalezeCount;
    private int ennemiCount;


    private static MexicanLogic singleInstance = null;

    public static MexicanLogic getSingleInstance() {
        if(singleInstance == null) singleInstance = new MexicanLogic();
        return singleInstance;
    }

    private MexicanLogic() {}

    public EnnemiPool getEnnemiPool() {
        return ennemiPool;
    }

    public EnnemiBalezePool getEnnemiBalezePool() {
        return ennemiBalezePool;
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
        instance.ennemiPool = new EnnemiPool(ass);
        instance.workerPool = new WorkerPool(ass);
        instance.ennemiBalezePool = new EnnemiBalezePool(ass);
        instance.dollarRecycler = new DollarRecycler(128);

        instance.disabledNPCs = false;
        instance.finishLoading();
    }

    public void finishLoading() {
        int numWorker = UpgradeManager.getSingleInstance().getUpgrade(UpgradeManager.WALL_WORKER).getLevel();
        for(int i = 0; i < numWorker; i++){
            addWorker();
        }
        for(int i = 0; i < ennemiCount; i++ ){
            Ennemi ennemi = ennemiPool.obtain();
            ennemi.getActions().removeRange(0, ennemi.getActions().size - 1);
            ennemi.setPosition(ennemi.getTargetX(), ennemi.getTargetY());
            ennemiGroup.addActor(ennemi);
            ennemiCount--; // ceux ajoutés ici sont compris dans ennemiCount donc il ne faut pas les compter un autre fois
        }
        for(int i = 0; i < ennemiBalezeCount; i++ ){
            EnnemiBaleze ennemiBaleze = ennemiBalezePool.obtain();
            ennemiBaleze.getActions().removeRange(0, ennemiBaleze.getActions().size - 1);
            ennemiBaleze.setPosition(ennemiBaleze.getTargetX(), ennemiBaleze.getTargetY());
            ennemiGroup.addActor(ennemiBaleze);
            ennemiBalezeCount--; // ceux ajoutés ici sont compris dans ennemiBalezeCount donc il ne faut pas les compter un autre fois
            // sinon la boucle ne se termine jamais
        }
    }

    public void init(double damage, double heal, double brickPower, double mul, AssetManager ass) {
        this.waveNumber = 0;
        this.damage = damage;
        this.heal = heal;
        this.brickPower = brickPower;
        this.mul = mul;
        this.trump = null;
        ennemiGroup = new Group();
        workerGroup = new Group();
        laserGroup = new Group();
        nukeExplosionGroup = new Group();
        brixplosionGroup = new Group();
        dayNightCycleGroup = new Group();
        disabledNPCs = false;
        bestWaveNumber = 0;

        ennemiToRemove = 0;
        this.ass = ass;
        ennemiPool = new EnnemiPool(ass);
        ennemiBalezePool = new EnnemiBalezePool(ass);
        workerPool = new WorkerPool(ass);
        dollarRecycler = new DollarRecycler(128);
        this.ennemiCount = 0;
        this.ennemiBalezeCount = 0;
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
        this.workerGroup.addActor(workerPool.obtain());
    }

    public void doDamage() {
        GameLogic.getSingleInstance().setHealth(
                 GameLogic.getSingleInstance().getHealth() - damage * mul
        );
    }

    public void doBalezeDamage() {
        double balezeDamage = (GameLogic.getSingleInstance().getMaxHealth())/5.0 + damage*100;
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
            double nbreDollar = UpgradeManager.getSingleInstance().getUpgrade(UpgradeManager.ACTIVE_CASH_RAIN).getLevel() * 1.1;
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
                for (Ennemi ennemi : ennemiPool.getShown()) {
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
            Array<Ennemi> toRemove = new Array<Ennemi>();
            for (Actor actor : ennemiGroup.getChildren()) {
                if (actor instanceof Ennemi && dynamite.getExplosionRadius().overlaps((((Ennemi) actor).getBounds()))) {
                    Ennemi temp = ((Ennemi) actor).kill(false);
                    toRemove.add(temp);
                }
            }
            for (Ennemi ennemi : toRemove) {
                ennemiPool.free(ennemi);
            }
        }
    }

    public void resetWaveTime(){elapsedTime = 0f;}

    public void updateWave(float delta) {
        if(GameLogic.getSingleInstance().isMenuUpgrade()) return;

        boolean isDay = GameLogic.getSingleInstance().isDay();

        elapsedTime += delta;
        // new wave every 45 seconds if day, every 25 seconds if night
        if ((elapsedTime >= 45f && isDay) || (elapsedTime >= 25f && !isDay) || waveNumber == 0) {
            elapsedTime = 0f;

            waveNumber++;
            if (waveNumber > bestWaveNumber)
                bestWaveNumber = waveNumber;
            if(waveNumber < 10) {   //PHASE 1
                for (int i = 0; i < 1 + waveNumber; i++) {
                    ennemiGroup.addActor(ennemiPool.obtain());
                }
            }
            else if (waveNumber < 20) { //PHASE 2
                for (int i = 0; i < 1 + waveNumber + (waveNumber-10) * 3; i++) {
                    ennemiGroup.addActor(ennemiPool.obtain());
                }
            }
            else{ //(waveNumber >=20) { //PHASE 3
                for (int i = 0; i <  waveNumber* 10; i++) {
                    ennemiGroup.addActor(ennemiPool.obtain());
                }
                for(int j = 20; j <= waveNumber; j++){
                    ennemiGroup.addActor(ennemiBalezePool.obtain());
                }
            }
        }
    }

    public void updateTrumpHead(Sun trump, Moon moon) {
        int duration = GameLogic.DAY_NIGHT_CYCLE_LEN;

        double dayTime = GameLogic.getSingleInstance().getDayNightTime();
        if (dayTime < duration / 2f) {
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

    public float getBestWaveNumber() {
        return bestWaveNumber;
    }

    public void setBestWaveNumber(float bestWaveNumber) {
        this.bestWaveNumber = bestWaveNumber;
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

    public int getEnnemiCount() {
        return ennemiCount;
    }

    public void setEnnemiCount(int ennemiCount) {
        this.ennemiCount = ennemiCount;
    }


    public int getEnnemiBalezeCount() {
        return ennemiBalezeCount;
    }

    public void setEnnemiBalezeCount(int ennemiBalezeCount) {
        this.ennemiBalezeCount = ennemiBalezeCount;
    }

    public void setDisabledNPCs(boolean disabled) {
        disabledNPCs = disabled;
    }

    public boolean isDisabledNPCs() {
        return disabledNPCs;
    }

    public WorkerPool getWorkerPool() {
        return workerPool;
    }

    public float getWaveNumber() {
        return waveNumber;
    }

}
