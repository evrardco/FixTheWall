package com.fixthewall.game;

import com.fixthewall.logic.BadGuysLogic;
import com.fixthewall.logic.WallLogic;
import com.fixthewall.screens.StartScreen;

public class Game extends com.badlogic.gdx.Game {

    public WallLogic wallLogic;
	@Override
	public void create () {
	    WallLogic.getSingleInstance().init(100.0f);
        BadGuysLogic.getSingleInstance().init(0.016f, 1.0f);
		setScreen(new StartScreen(this));



	}

}
