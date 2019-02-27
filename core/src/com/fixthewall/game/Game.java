package com.fixthewall.game;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fixthewall.logic.BadGuysLogic;
import com.fixthewall.logic.MusicLogic;
import com.fixthewall.logic.WallLogic;
import com.fixthewall.screens.StartScreen;

public class Game extends com.badlogic.gdx.Game {

    public FitViewport viewport;

    public static final float GAME_WIDTH = 1080;
    public static final int GAME_HEIGHT = 1920;

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
		viewport = new FitViewport(Game.GAME_WIDTH, Game.GAME_HEIGHT);
		setScreen(new StartScreen(this));
	}
	@Override
	public void dispose (){
		playlist.dispose();
	}
}
