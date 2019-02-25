package com.fixthewall.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.fixthewall.screens.StartScreen;


public class FixTheWall extends Game {
    @Override
    public void create(){
        Gdx.app.log("FixTheWall", "Created !");
        setScreen(new StartScreen());
    }
}
