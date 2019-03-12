package com.fixthewall.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fixthewall.game.screens.GameScreen;
import com.fixthewall.game.screens.LoadingScreen;
import com.fixthewall.game.upgrades.AbstractManager;
import com.fixthewall.game.logic.BadGuysLogic;
import com.fixthewall.game.logic.MusicLogic;
import com.fixthewall.game.logic.GameLogic;
import com.fixthewall.game.screens.StartScreen;

public class Game extends com.badlogic.gdx.Game {

    public FitViewport viewport;
    public AssetManager ass;

    public static final float GAME_WIDTH = 1080;
    public static final int GAME_HEIGHT = 1920;

	private MusicLogic playlist;

	@Override
	public void create () {
		//music
		playlist = new MusicLogic();
		playlist.runPlaylist();
		//
        ass = new AssetManager();
        FileHandleResolver resolver = new InternalFileHandleResolver();
        ass.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        ass.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));


        loadVIPAssets();
        loadAssets();

        AbstractManager.getSingleInstance();
        GameLogic.getSingleInstance().init();
        BadGuysLogic.getSingleInstance().init(9.0, 1.0f);
		viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT);
		setScreen(new LoadingScreen(this));
	}
	@Override
	public void dispose (){
		playlist.dispose();
		ass.dispose();
	}

    /**
     * @pre: VIP assets only!!! ಠ_ಠ
     * @post: private closed club for loading vip assets ;)
     */
	public void loadVIPAssets() {
        // First, let's define the params and then load our bigger font
        FreetypeFontLoader.FreeTypeFontLoaderParameter smallFont
                = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        smallFont.fontFileName = "data/Germania.ttf";
        smallFont.fontParameters.size = 30;
        smallFont.fontParameters.color = Color.WHITE;

        ass.load("GermaniaLoading.ttf", BitmapFont.class, smallFont);

        ass.finishLoading();
    }

	public void loadAssets() {

        ass.load("badGuysRSheet.png", Texture.class);
        ass.load("fondWall.png", Texture.class);
        ass.load("fondWall-nuit.png", Texture.class);
        ass.load("marteau.png", Texture.class);
        ass.load("pioche.png", Texture.class);
        ass.load("texture_button.png", Texture.class);
        ass.load("texture_button_return.png", Texture.class);
        ass.load("texture_button_return_down.png", Texture.class);

        // dossier wallStates
        ass.load("wallStates/theWall.png", Texture.class);
        ass.load("wallStates/theWall20.png", Texture.class);
        ass.load("wallStates/theWall40.png", Texture.class);
        ass.load("wallStates/theWall60.png", Texture.class);
        ass.load("wallStates/theWall80.png", Texture.class);
        ass.load("wallStates/theWallBroken.png", Texture.class);
        ass.load("nuke.png", Texture.class);

        // dossier music
        ass.load("music/song1.mp3", Music.class);
        ass.load("music/song2.mp3", Music.class);
        ass.load("music/song3.mp3", Music.class);
        ass.load("music/song4.mp3", Music.class);
        ass.load("music/song5.mp3", Music.class);
        ass.load("music/song6.mp3", Music.class);

        // dossier Frames
        ass.load("Frames/SheetFrameEnnemi.png", Texture.class);
        ass.load("Frames/SheetFrameEnnemiHit.png", Texture.class);

        // dossier data


        // Next, let's define the params and then load our medium font
        FreetypeFontLoader.FreeTypeFontLoaderParameter loadingFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        loadingFont.fontFileName = "data/Germania.ttf";
        loadingFont.fontParameters.size = 30;
        loadingFont.fontParameters.color = Color.BLACK;
        ass.load("Germania30.ttf", BitmapFont.class, loadingFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter mediumFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mediumFont.fontFileName = "data/Germania.ttf";
        mediumFont.fontParameters.size = 60;
        mediumFont.fontParameters.color = Color.BLACK;
        ass.load("Germania60.ttf", BitmapFont.class, mediumFont);

        // Next, let's define the params and then load our bigger font
        FreetypeFontLoader.FreeTypeFontLoaderParameter bigFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        bigFont.fontFileName = "data/Germania.ttf";
        bigFont.fontParameters.size = 120;
        ass.load("Germania120.ttf", BitmapFont.class, bigFont);

    }
}
