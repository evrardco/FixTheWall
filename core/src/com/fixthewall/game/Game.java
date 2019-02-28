package com.fixthewall.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fixthewall.logic.BadGuysLogic;
import com.fixthewall.logic.WallLogic;
import com.fixthewall.screens.StartScreen;

public class Game extends com.badlogic.gdx.Game {

	private int sizePlaylist;
	private Music [] playlist;
	public FitViewport viewport;

	public static final float GAME_WIDTH = 1080;
	public static final int GAME_HEIGHT = 1920;

    public WallLogic wallLogic;
	@Override
	public void create () {
		//music
		sizePlaylist = 6;
		playlist = new Music[sizePlaylist];
		playlist[0] = Gdx.audio.newMusic(Gdx.files.internal("music/song1.mp3"));
		playlist[0].setVolume(0.6f);
		playlist[1] = Gdx.audio.newMusic(Gdx.files.internal("music/song2.mp3"));
		playlist[1].setVolume(0.35f);
		playlist[2] = Gdx.audio.newMusic(Gdx.files.internal("music/song3.mp3"));
		playlist[2].setVolume(0.6f);
		playlist[3] = Gdx.audio.newMusic(Gdx.files.internal("music/song4.mp3"));
		playlist[3].setVolume(0.6f);
		playlist[4] = Gdx.audio.newMusic(Gdx.files.internal("music/song5.mp3"));
		playlist[4].setVolume(0.5f);
		playlist[5] = Gdx.audio.newMusic(Gdx.files.internal("music/song6.mp3"));
		playlist[5].setVolume(0.55f);
		runMusic();
		//

	    WallLogic.getSingleInstance().init(100.5f);
        BadGuysLogic.getSingleInstance().init(0.016f, 1.0f);
		viewport = new FitViewport(Game.GAME_WIDTH, Game.GAME_HEIGHT);
		setScreen(new StartScreen(this));
	}
	@Override
	public void dispose (){
		for (Music m : playlist) {
			m.dispose();
		}
	}

	private void runMusic() {
		//Lancement
		getRandomMusic().play();
		//Gestion des transitions
		playlist[0].setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				getRandomMusic().play();
				playlist[0].setVolume(0.6f);
			}
		});
		playlist[1].setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				getRandomMusic().play();
				playlist[1].setVolume(0.35f);
			}
		});
		playlist[2].setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				getRandomMusic().play();
				playlist[2].setVolume(0.6f);
			}
		});
		playlist[3].setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				getRandomMusic().play();
				playlist[3].setVolume(0.6f);
			}
		});
		playlist[4].setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				getRandomMusic().play();
				playlist[4].setVolume(0.5f);
			}
		});
		playlist[5].setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				getRandomMusic().play();
				playlist[5].setVolume(0.55f);
			}
		});
		//Gestion des fondus audio
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				if (playlist[3].isPlaying()) {
					if (playlist[3].getPosition() >= 254) {
						playlist[3].setVolume(playlist[3].getVolume() - 0.05f);
					}
				}
				if (playlist[4].isPlaying()) {
					if (playlist[4].getPosition() >= 178) {
						playlist[4].setVolume(playlist[4].getVolume() - 0.05f);
					}
				}
			}
		}, 5, 0.2f, 20);
	}

	private Music getRandomMusic(){
		int rand = getRandom(sizePlaylist);
		if (rand >= sizePlaylist){rand = sizePlaylist - 1;}
		return playlist[rand];
	}

	/*
	* Retourne un entier entre 0 et n-1
	*/
	public static int getRandom(int n){
		return (int)(Math.random()*n);//pour 6 music ==> n=7;
	}

}
