package com.fixthewall.game;

import com.fixthewall.game.logic.BadGuysLogic;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.upgrades.UpgradeManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Perziztancinator implements Serializable {

    private transient static Perziztancinator instance;
    private GameLogic logic;
    private BadGuysLogic badLogic;
    private UpgradeManager upgManager;
    public static final String SAVE_FILE_NAME = "/tmp/fixTheWall.sav";

    public Perziztancinator getSingleInstance(){
        if(instance == null) instance = new Perziztancinator();
        return instance;
    }

    private Perziztancinator(){}

    public void init(){
        this.logic = GameLogic.getSingleInstance();
        this.badLogic = BadGuysLogic.getSingleInstance();
        this.upgManager = UpgradeManager.getSingleInstance();
    }

    public void save(){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(instance);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in "+SAVE_FILE_NAME);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void load(){
        try {
            FileInputStream fileIn = new FileInputStream(SAVE_FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            instance = (Perziztancinator) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
    }



}
