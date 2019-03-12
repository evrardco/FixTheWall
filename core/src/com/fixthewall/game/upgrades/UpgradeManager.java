package com.fixthewall.game.upgrades;

public class UpgradeManager {

   private AbstractUpgrade [] allUpgrade = new AbstractUpgrade[10];
   private static UpgradeManager singleInstance = null;

   private UpgradeManager() {}

    public AbstractUpgrade[] getAllUpgrade() {
        return allUpgrade;
    }
    public void init(){
        AbstractUpgrade currentUpgrade = null;
        for (int i = 0; i < allUpgrade.length; i++) {
            currentUpgrade = new Upgrade1(0, 10);
            allUpgrade[i] = currentUpgrade;
            currentUpgrade.setLevel(0);

        }
        update();
    }


    public void update()
    {
        AbstractUpgrade currentUpgrade = null;
        for (int i = 0; i < allUpgrade.length; i++)
        {
            currentUpgrade = allUpgrade[i];
            if (currentUpgrade.isApplied())
            {
                currentUpgrade.apply();
            }
        }

    }

    public void unupdate()
    {
        AbstractUpgrade currentUpgrade = null;
        for (int i = 0; i < allUpgrade.length; i++)
        {
            currentUpgrade = allUpgrade[i];
            currentUpgrade.unApply();
        }

    }

    public double totalSum()
    {
        double totalCost = 0;
        AbstractUpgrade currentUpgrade = null;
        for (int i = 0; i < allUpgrade.length; i++)
        {
            currentUpgrade = allUpgrade[i];
            totalCost = totalCost+currentUpgrade.getCost();
        }
        return totalCost;


    }

    public static UpgradeManager getSingleInstance(){
        if(singleInstance == null) singleInstance = new UpgradeManager();
        return singleInstance;
    }

}
