package com.fixthewall.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Timer;
import com.fixthewall.logic.BadGuysLogic;
import com.fixthewall.logic.WallLogic;
import com.fixthewall.screens.StartScreen;

public class Game extends com.badlogic.gdx.Game {
	private Music song1;
	private Music song2;
	private Music song3;
	private Music song4;
	private Music song5;
	private Music song6;

    public WallLogic wallLogic;
	@Override
	public void create () {
		//music
		song1 = Gdx.audio.newMusic(Gdx.files.internal("music/song1.mp3"));
		song2 = Gdx.audio.newMusic(Gdx.files.internal("music/song2.mp3"));
		song3 = Gdx.audio.newMusic(Gdx.files.internal("music/song3.mp3"));
		song4 = Gdx.audio.newMusic(Gdx.files.internal("music/song4.mp3"));
		song5 = Gdx.audio.newMusic(Gdx.files.internal("music/song5.mp3"));
		song6 = Gdx.audio.newMusic(Gdx.files.internal("music/song6.mp3"));
		runMusic();
		//

	    WallLogic.getSingleInstance().init(100.5f);
        BadGuysLogic.getSingleInstance().init(0.016f, 1.0f);
		setScreen(new StartScreen(this));



	}
	@Override
	public void dispose (){
		song1.dispose();
		song2.dispose();
		song3.dispose();
		song4.dispose();
		song5.dispose();
		song6.dispose();
	}

	private void runMusic() {
		//Lancement
		song1.setVolume(0.8f);
		song1.play();
		//Gestion des transitions
		song1.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				song2.play();
			}
		});
		song2.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				song3.play();
			}
		});
		song3.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				song4.play();
			}
		});
		song4.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				song5.play();
			}
		});
		song5.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				song6.play();
			}
		});
		song6.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				song1.play();
			}
		});
		//Gestion des fondus audio
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				if (song1.isPlaying()) {
					if (song1.getPosition() >= 215) {
						song1.setVolume(song1.getVolume() - 0.1f);
					}
				}
				if (song2.isPlaying()) {
					if (song2.getPosition() >= 192) {
						song2.setVolume(song2.getVolume() - 0.1f);
					}
				}
				if (song3.isPlaying()) {
					if (song3.getPosition() >= 143) {
						song3.setVolume(song3.getVolume() - 0.1f);
					}
				}
				if (song4.isPlaying()) {
					if (song4.getPosition() >= 249) {
						song4.setVolume(song4.getVolume() - 0.1f);
					}
				}
				if (song5.isPlaying()) {
					if (song5.getPosition() >= 173) {
						song5.setVolume(song5.getVolume() - 0.1f);
					}
				}
				if (song6.isPlaying()) {
					if (song6.getPosition() >= 231) {
						song6.setVolume(song6.getVolume() - 0.1f);
					}
				}
				if (!song1.isPlaying()) {
					song1.setVolume(0.8f);
				}
				if (!song2.isPlaying()) {
					song2.setVolume(0.8f);
				}
				if (!song3.isPlaying()) {
					song3.setVolume(0.8f);
				}
				if (!song4.isPlaying()) {
					song4.setVolume(0.8f);
				}
				if (!song5.isPlaying()) {
					song5.setVolume(0.8f);
				}
				if (!song6.isPlaying()) {
					song6.setVolume(0.8f);
				}
			}
		}, 10, 0.4f, 20);
	}

}
