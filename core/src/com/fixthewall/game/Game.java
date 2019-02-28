package com.fixthewall.game;

import com.fixthewall.logic.BadGuysLogic;
import com.fixthewall.logic.MusicLogic;
import com.fixthewall.logic.WallLogic;
import com.fixthewall.screens.StartScreen;

public class Game extends com.badlogic.gdx.Game {

	private MusicLogic playlist;

    public WallLogic wallLogic;
	@Override
	public void create () {
		//music
		playlist = new MusicLogic();
		playlist.runPlaylist();
		//

	    WallLogic.getSingleInstance().init(100.5f);
        BadGuysLogic.getSingleInstance().init(0.016f, 1.0f);
		setScreen(new StartScreen(this));



	}
	@Override
	public void dispose (){
		playlist.dispose();
	}
}
