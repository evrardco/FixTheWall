package com.fixthewall.game.upgrades;

import com.badlogic.gdx.assets.AssetManager;
import com.fixthewall.game.logic.GameLogic;

import java.io.Serializable;

public class UpgradeManager implements Serializable {

    private int levelUpgradeHammer;

    private AbstractUpgrade [] allUpgrade = new AbstractUpgrade[8];
    private static UpgradeManager singleInstance = null;

    private UpgradeManager() {}

    public AbstractUpgrade[] getAllUpgrade() {
        return allUpgrade;
    }

    public void init(AssetManager ass) {
        allUpgrade[0] = new UpgradeBricks(0, 50);//Bricks++
        allUpgrade[1] = new UpgradeHP(0, 50);//HP++
        allUpgrade[2] = new UpgradeLife(0, 500);//Life++
        allUpgrade[3] = new WorkerUpgrade(0, 200, ass);
        allUpgrade[4] = new WorkerLevelUpgrade(0, 500);
        allUpgrade[5] = new UpgradeCashRain(0, 50);//CashRain
        allUpgrade[6] = new TrumpUpgrade(0, 1000);
        allUpgrade[7] = new NukeUpgrade(0, 10000000);
        update();
    }


    public void update() {
        AbstractUpgrade currentUpgrade;
        for (int i = 0; i < allUpgrade.length; i++) {
            currentUpgrade = allUpgrade[i];
            if (currentUpgrade.isApplied()) {
                currentUpgrade.apply();
                currentUpgrade.setApplied(false);//Permet de ne pas upgrade plusieurs à la foi.
            }
        }

    }

    public void unupdate() {
        AbstractUpgrade currentUpgrade;
        for (int i = 0; i < allUpgrade.length; i++) {
            currentUpgrade = allUpgrade[i];
            currentUpgrade.unApply();
        }

    }

    public double totalSum() {
        double totalCost = 0;
        AbstractUpgrade currentUpgrade;
        for (int i = 0; i < allUpgrade.length; i++) {
            currentUpgrade = allUpgrade[i];
            totalCost = totalCost+currentUpgrade.getCost();
        }
        return totalCost;


    }

    public void increaseLevelUpgradeHammer() {
        levelUpgradeHammer++;
        if(levelUpgradeHammer<=70) {
            GameLogic.getSingleInstance().setHammerLevel(levelUpgradeHammer/10);
        }
    }

    public static UpgradeManager getSingleInstance() {
        if(singleInstance == null) singleInstance = new UpgradeManager();
        return singleInstance;
    }

    public void init(UpgradeManager instance){
        singleInstance = instance;
    }

}
