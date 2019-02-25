package com.fixthewall.game;

import com.fixthewall.logic.WallLogic;
import com.fixthewall.screens.StartScreen;

public class Game extends com.badlogic.gdx.Game {

    public WallLogic wallLogic;
	@Override
	public void create () {
	    wallLogic = new WallLogic(100);
		setScreen(new StartScreen(this));


	}

}
