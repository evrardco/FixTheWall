package com.fixthewall.game;

import com.badlogic.gdx.Gdx;
import com.fixthewall.game.logic.MexicanLogic;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.upgrades.UpgradeManager;

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
            Gdx.app.log("Perziztancinator","Serialized data is saved in "+SAVE_FILE_NAME);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void load(){
        try {
            InputStream fileIn = Gdx.files.local(SAVE_FILE_NAME).read();
            ObjectInputStream in = new ObjectInputStream(fileIn);
            instance = (Perziztancinator) in.readObject();
            instance.gameLoaded = true;
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
    public static boolean isCorrupt(){
        return instance == null ||
                instance.logic == null ||
                instance.upgManager == null ||
                instance.badLogic == null;
    }

    private transient static Perziztancinator instance;

    public boolean isGameLoaded() {
        return gameLoaded;
    }

    public void setGameLoaded(boolean gameLoaded) {
        this.gameLoaded = gameLoaded;
    }

    private GameLogic logic;
    private MexicanLogic badLogic;
    private UpgradeManager upgManager;
    private boolean gameLoaded;

    public GameLogic getLogic() {
        return logic;
    }

    public MexicanLogic getBadLogic() {
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
        this.badLogic = MexicanLogic.getSingleInstance();
        this.upgManager = UpgradeManager.getSingleInstance();
    }









}
