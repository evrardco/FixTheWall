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
import com.fixthewall.game.screens.LoadingScreen;
import com.fixthewall.game.upgrades.UpgradeManager;
import com.fixthewall.game.logic.BadGuysLogic;
import com.fixthewall.game.logic.MusicLogic;
import com.fixthewall.game.logic.GameLogic;

public class Game extends com.badlogic.gdx.Game {

    public FitViewport viewport;
    public AssetManager ass;

    public static final float GAME_WIDTH = 1080;
    public static final int GAME_HEIGHT = 1920;

	public MusicLogic playlist;

	@Override
	public void create () {
        ass = new AssetManager();
        FileHandleResolver resolver = new InternalFileHandleResolver();
        ass.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        ass.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));


        loadVIPAssets();
        loadAssets();

        UpgradeManager.getSingleInstance();
        GameLogic.getSingleInstance().init();
        BadGuysLogic.getSingleInstance().init(3.0, 1.0f);
		viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT);
		setScreen(new LoadingScreen(this));
	}
	@Override
	public void dispose () {
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

        // dossier logoGG
        // (voir pour les perfs si c'est pas mieux de faire une seule Texture et choper les images en TextureRegion)
        ass.load("logoGG/logoGG_0.png", Texture.class);
        ass.load("logoGG/logoGG_1.png", Texture.class);
        ass.load("logoGG/logoGG_2.png", Texture.class);
        ass.load("logoGG/logoGG_3.png", Texture.class);
        ass.load("logoGG/logoGG_4.png", Texture.class);
        ass.load("logoGG/logoGG_5.png", Texture.class);
        ass.load("logoGG/logoGG_6.png", Texture.class);
        ass.load("logoGG/logoGG_7.png", Texture.class);
        ass.load("logoGG/logoGG_8.png", Texture.class);
        ass.load("logoGG/logoGG_9.png", Texture.class);
        ass.load("logoGG/logoGG_10.png", Texture.class);
        ass.load("logoGG/logoGG_11.png", Texture.class);
        ass.load("logoGG/logoGG_12.png", Texture.class);

        ass.finishLoading();
    }

	public void loadAssets() {

        ass.load("badGuysRSheet.png", Texture.class);
        ass.load("fondWall.png", Texture.class);
        ass.load("fondWall-nuit.png", Texture.class);
        ass.load("nuages.png", Texture.class);
        ass.load("marteau.png", Texture.class);
        ass.load("pioche.png", Texture.class);

        // dossier ui
        ass.load("ui/texture_button.png", Texture.class);
        ass.load("ui/texture_button_return.png", Texture.class);
        ass.load("ui/texture_button_return_down.png", Texture.class);
        ass.load("ui/texture_button_settings.png", Texture.class);
        ass.load("ui/texture_button_settings_down.png", Texture.class);
        ass.load("ui/texture_button_volume.png", Texture.class);
        ass.load("ui/texture_button_volume_down.png", Texture.class);
        ass.load("ui/white_background.png", Texture.class);

        // dossier wallStates
        ass.load("wallStates/theWall.png", Texture.class);
        ass.load("wallStates/theWall20.png", Texture.class);
        ass.load("wallStates/theWall40.png", Texture.class);
        ass.load("wallStates/theWall60.png", Texture.class);
        ass.load("wallStates/theWall80.png", Texture.class);
        ass.load("wallStates/theWallBroken.png", Texture.class);

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
