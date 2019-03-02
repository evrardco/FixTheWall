package com.fixthewall.game.upgrades;

public class AbstractManager {

   private AbstractUpgrade [] allUpgrade = new AbstractUpgrade[100];
    private static AbstractManager singleInstance = null;

   public AbstractManager()
   {
       AbstractUpgrade currentUpgrade = null;
       for (int i = 0; i < allUpgrade.length; i++) {
           currentUpgrade = new Upgrade1(0, 0);
           allUpgrade[i] = currentUpgrade;
           currentUpgrade.setLevel(0);
       }

   }

    public void update()
    {
        AbstractUpgrade currentUpgrade = null;
        for (int i = 0; i < allUpgrade.length; i++)
        {
            currentUpgrade = allUpgrade[i];
            currentUpgrade.apply();
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

    {
        AbstractUpgrade currentUpgrade = null;
        for (int i = 0; i < allUpgrade.length; i++)
        {
            currentUpgrade = allUpgrade[i];
            currentUpgrade.unApply();
        }

    }
    public static AbstractManager getSingleInstance(){
        if(singleInstance == null) singleInstance = new AbstractManager();
        return singleInstance;
    }

}
