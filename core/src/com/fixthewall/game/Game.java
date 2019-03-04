package com.fixthewall.game;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fixthewall.game.screens.GameScreen;
import com.fixthewall.game.upgrades.AbstractManager;
import com.fixthewall.game.logic.BadGuysLogic;
import com.fixthewall.game.logic.MusicLogic;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.screens.StartScreen;

public class Game extends com.badlogic.gdx.Game {

    public FitViewport viewport;

    public static final float GAME_WIDTH = 1080;
    public static final int GAME_HEIGHT = 1920;

	private MusicLogic playlist;

	@Override
	public void create () {
		//music
		playlist = new MusicLogic();
		playlist.runPlaylist();
		//

        AbstractManager.getSingleInstance();
        GameLogic.getSingleInstance().init();
        BadGuysLogic.getSingleInstance().init(9.0, 1.0f);
		viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT);
		setScreen(new StartScreen(this));
	}
	@Override
	public void dispose (){
		playlist.dispose();
	}
}
