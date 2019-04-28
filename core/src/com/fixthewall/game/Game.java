package com.fixthewall.game;

import com.badlogic.gdx.Gdx;
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
import com.fixthewall.game.logic.MexicanLogic;
import com.fixthewall.game.screens.LoadingScreen;
import com.fixthewall.game.upgrades.UpgradeManager;
import com.fixthewall.game.logic.MusicLogic;
import com.fixthewall.game.logic.GameLogic;

import javax.xml.soap.Text;

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
        //We see whether we must load the saved game.
        if(!Perziztancinator.isNewGame()) Perziztancinator.load();
        if(Perziztancinator.isNewGame() || Perziztancinator.isCorrupt() ){
            UpgradeManager.getSingleInstance().init(ass);
            GameLogic.getSingleInstance().init();
            MexicanLogic.getSingleInstance().init(1.0, 1.0, 1.0, 1.0, this.ass);
            Perziztancinator.getSingleInstance().init();
            Gdx.app.log("Game", "Done new game.");


        }else {
            UpgradeManager.getSingleInstance().init(Perziztancinator.getSingleInstance().getUpgManager());
            GameLogic.getSingleInstance().init(Perziztancinator.getSingleInstance().getLogic());
            MexicanLogic.init(Perziztancinator.getSingleInstance().getBadLogic(), this.ass);
            Gdx.app.log("Game", "Done loading.");

        }

		viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT);
		setScreen(new LoadingScreen(this));

	}

	@Override
	public void dispose () {
		ass.dispose();
	}

    @Override
    public void pause() {
        super.pause();
        Perziztancinator.save();
        Gdx.app.log("GAMELOGIC", "Saving game...");
    }

    /**
     * @pre: VIP assets only!!! ಠ_ಠ
     * @post: private closed club for loading vip assets ;)
     */
	public void loadVIPAssets() {
        // First, let's define the params and then load our bigger font
        FreetypeFontLoader.FreeTypeFontLoaderParameter smallFont
                = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        smallFont.fontFileName = "font/PoetsenOne-Regular.ttf";
        smallFont.fontParameters.size = 30;
        smallFont.fontParameters.color = Color.WHITE;

        ass.load("PoetsenOne_loading.ttf", BitmapFont.class, smallFont);

        // dossier logoGG
        // (voir pour les perfs si c'est pas mieux de faire une seule Texture et choper les images en TextureRegion)
        ass.load("logoGG/logoGG_background.png", Texture.class);
        ass.load("logoGG/logoGG_brick.png", Texture.class);

        ass.finishLoading();
    }

	public void loadAssets() {

        ass.load("fondWall.png", Texture.class);
        ass.load("fondWall-nuit.png", Texture.class);
        ass.load("imgPause.png", Texture.class);
        ass.load("imgPauseFond.png", Texture.class);
        ass.load("nuages.png", Texture.class);
        ass.load("pioche.png", Texture.class);
        ass.load("laser.png", Texture.class);
        ass.load("dollar.png", Texture.class);
        ass.load("Sun.png", Texture.class);
        ass.load("echelle.png", Texture.class);
        ass.load("trump.png", Texture.class);
        ass.load("TheMoon.png", Texture.class);
        //dossier nuke
        ass.load("nuke/nuke.png", Texture.class);
        //dossier fx
        ass.load("fx/blinding_foreground.png", Texture.class);
        ass.load("fx/fusion.png", Texture.class);
        // dossier anim
        ass.load("anim/brick.png", Texture.class);
        ass.load("anim/explosion/flame.png", Texture.class);
        ass.load("anim/explosion/smoke.png", Texture.class);

        // dossier Marteaux
        ass.load("Marteaux/Ghal_Maraz.png", Texture.class);
        ass.load("Marteaux/marteau.png", Texture.class);
        ass.load("Marteaux/marteau_Brique.png", Texture.class);
        ass.load("Marteaux/marteau_normal.png", Texture.class);
        ass.load("Marteaux/Marteau_thor.png", Texture.class);
        ass.load("Marteaux/marteau_Trump.png", Texture.class);
        ass.load("Marteaux/marteauDiam.png", Texture.class);
        ass.load("Marteaux/War_hammer.png", Texture.class);

        // dossier ui
        ass.load("ui/texture_button.png", Texture.class);
        ass.load("ui/texture_button_down.png", Texture.class);
        ass.load("ui/texture_button_disabled.png", Texture.class);
        ass.load("ui/texture_button_return.png", Texture.class);
        ass.load("ui/texture_button_return_down.png", Texture.class);
        ass.load("ui/texture_button_settings.png", Texture.class);
        ass.load("ui/texture_button_settings_down.png", Texture.class);
        ass.load("ui/texture_button_volume.png", Texture.class);
        ass.load("ui/texture_button_volume_down.png", Texture.class);
        ass.load("ui/texture_button_volume_off.png", Texture.class);
        ass.load("ui/texture_button_volume_off_down.png", Texture.class);
        ass.load("ui/background.png", Texture.class);
        ass.load("ui/texture_upgrade_panel_hammer.png", Texture.class);
        ass.load("ui/texture_upgrade_panel_wall.png", Texture.class);
        ass.load("ui/texture_upgrade_panel_other.png", Texture.class);
        ass.load("ui/texture_progressbar_background.png", Texture.class);
        ass.load("ui/texture_progressbar.png", Texture.class);
        ass.load("ui/texture_button_play.png", Texture.class);
        ass.load("ui/texture_button_play_down.png", Texture.class);
        ass.load("ui/texture_button_deletesave.png", Texture.class);
        ass.load("ui/texture_button_deletesave_down.png", Texture.class);

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
        ass.load("Frames/SheetFrameDynamite.png", Texture.class);
        ass.load("Frames/SheetFrameWorker.png", Texture.class);
        ass.load("Frames/SheetFrameBaleze.png", Texture.class);
        ass.load("Frames/SheetFrameBalezeHit.png", Texture.class);
        ass.load("Frames/SheetFrameCash.png", Texture.class);
        ass.load("Frames/SheetFrameSun.png", Texture.class);
        ass.load("Frames/SheetFrameDollar.png", Texture.class);

        // dossier font
        FreetypeFontLoader.FreeTypeFontLoaderParameter smallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        smallFont.fontFileName = "font/PoetsenOne-Regular.ttf";
        smallFont.fontParameters.size = 30;
        ass.load("PoetsenOne30.ttf", BitmapFont.class, smallFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter smallFontOutline = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        smallFontOutline.fontFileName = "font/PoetsenOne-Regular.ttf";
        smallFontOutline.fontParameters.size = 30;
        smallFontOutline.fontParameters.color = Color.WHITE;
        smallFontOutline.fontParameters.borderColor = Color.BLACK;
        smallFontOutline.fontParameters.borderWidth = 3;
        ass.load("PoetsenOne30_outline.ttf", BitmapFont.class, smallFontOutline);

        FreetypeFontLoader.FreeTypeFontLoaderParameter mediumFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mediumFont.fontFileName = "font/PoetsenOne-Regular.ttf";
        mediumFont.fontParameters.size = 50;
        ass.load("PoetsenOne50.ttf", BitmapFont.class, mediumFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter bigFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        bigFont.fontFileName = "font/PoetsenOne-Regular.ttf";
        bigFont.fontParameters.size = 120;
        ass.load("PoetsenOne120.ttf", BitmapFont.class, bigFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter titleFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        titleFont.fontFileName = "font/gomarice_nandaka_western.ttf";
        titleFont.fontParameters.size = 100;
        titleFont.fontParameters.color = Color.BLACK;
        titleFont.fontParameters.borderColor = Color.WHITE;
        titleFont.fontParameters.borderWidth = 10;
        ass.load("Western_outline.ttf", BitmapFont.class, titleFont);
    }
}
