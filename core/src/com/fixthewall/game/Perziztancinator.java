package com.fixthewall.game;

import com.badlogic.gdx.Gdx;
import com.fixthewall.game.logic.BadGuysLogic;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.upgrades.UpgradeManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class Perziztancinator implements Serializable {

    public static void save(){

        try {
            OutputStream fileOut = Gdx.files.local(SAVE_FILE_NAME).write(false);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(instance);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in "+SAVE_FILE_NAME);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void load(){
        try {
            InputStream fileIn = Gdx.files.local(SAVE_FILE_NAME).read();
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

    public static boolean isNewGame(){
        boolean ret = !Gdx.files.local(SAVE_FILE_NAME).exists();
        return ret;
    }

    private transient static Perziztancinator instance;


    private GameLogic logic;
    private BadGuysLogic badLogic;
    private UpgradeManager upgManager;

    public GameLogic getLogic() {
        return logic;
    }

    public BadGuysLogic getBadLogic() {
        return badLogic;
    }

    public UpgradeManager getUpgManager() {
        return upgManager;
    }

    public static final String SAVE_FILE_NAME = "/tmp/fixTheWall.sav";

    public static Perziztancinator getSingleInstance(){
        if(instance == null) instance = new Perziztancinator();
        return instance;
    }

    private Perziztancinator(){}

    public void init(){
        this.logic = GameLogic.getSingleInstance();
        this.badLogic = BadGuysLogic.getSingleInstance();
        this.upgManager = UpgradeManager.getSingleInstance();
    }









}
