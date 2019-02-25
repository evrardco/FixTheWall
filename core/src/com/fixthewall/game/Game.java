package com.fixthewall.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fixthewall.screens.StartScreen;

public class Game extends com.badlogic.gdx.Game {


	@Override
	public void create () {
		setScreen(new StartScreen(this));
	}

}
