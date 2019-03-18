package com.fixthewall.game.upgrades;

public class UpgradeManager {

   private AbstractUpgrade [] allUpgrade = new AbstractUpgrade[2];
   private static UpgradeManager singleInstance = null;

   private UpgradeManager() {}

    public AbstractUpgrade[] getAllUpgrade() {
        return allUpgrade;
    }
    public void init(){
        allUpgrade[0] = new Upgrade1(0, 10);
        allUpgrade[1] = new Upgrade2(0, 100);

        update();
    }


    public void update()
    {
        AbstractUpgrade currentUpgrade;
        for (int i = 0; i < allUpgrade.length; i++)
        {
            currentUpgrade = allUpgrade[i];
            if (currentUpgrade.isApplied())
            {
                currentUpgrade.apply();
                currentUpgrade.setApplied(false);//Permet de ne pas upgrade plusieurs à la foi.
            }
        }

    }

    public void unupdate()
    {
        AbstractUpgrade currentUpgrade;
        for (int i = 0; i < allUpgrade.length; i++)
        {
            currentUpgrade = allUpgrade[i];
            currentUpgrade.unApply();
        }

    }

    public double totalSum()
    {
        double totalCost = 0;
        AbstractUpgrade currentUpgrade;
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
