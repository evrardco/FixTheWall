package com.fixthewall.game.upgrades;

import java.io.Serializable;

public class UpgradeManager implements Serializable {

    public static final int HAMMER_BRICKS = 0;
    public static final int HAMMER_LIFE = 1;
    public static final int WALL_LIFE = 2;
    public static final int WALL_WORKER = 3;
    public static final int WALL_WORKER_LEVEL = 4;
    public static final int ACTIVE_CASH_RAIN = 5;
    public static final int ACTIVE_TRUMP = 6;
    public static final int ACTIVE_NUKE = 7;

    private AbstractUpgrade [] allUpgrade = new AbstractUpgrade[8];
    private static UpgradeManager singleInstance = null;

    private UpgradeManager() {}

    public AbstractUpgrade[] getAllUpgrade() {
        return allUpgrade;
    }

    public AbstractUpgrade getUpgrade(int upgrade) {
        if (upgrade < 0 || upgrade >= getAllUpgrade().length)
            return null;
        else
            return getAllUpgrade()[upgrade];
    }

    public void init() {
        allUpgrade[0] = new UpgradeBricks(0, 200);//Bricks++
        allUpgrade[1] = new UpgradeHP(0, 100);//HP++
        allUpgrade[2] = new UpgradeLife(0, 500);//Life++
        allUpgrade[3] = new WorkerUpgrade(0, 200);
        allUpgrade[4] = new WorkerLevelUpgrade(0, 500);
        allUpgrade[5] = new UpgradeCashRain(0, 10000.0);//CashRain
        allUpgrade[6] = new TrumpUpgrade(0, 1000);
        allUpgrade[7] = new NukeUpgrade(0, 0);
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

    public static UpgradeManager getSingleInstance() {
        if(singleInstance == null) singleInstance = new UpgradeManager();
        return singleInstance;
    }

    public void init(UpgradeManager instance){
        singleInstance = instance;
    }

}
