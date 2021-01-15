package com.nicokla.musicos2.PlayerFrag.LibgdxStuff;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;

import com.nicokla.musicos2.Firebase.SongStorage;
import com.nicokla.musicos2.MainAndCo.GlobalVars;
import com.nicokla.musicos2.MainAndCo.MainActivity;


public class PianoTiles extends Game {
	public final static String MASTER_PATH = "pack.atlas"; //"master.pack";
//	public final static int WORLD_WIDTH = 700;
//	public final static int WORLD_HEIGHT = 400;

	private AssetManager assetManager;
	public GameScreen gameScreen;
	public MainActivity mainActivity;

	public PianoTiles(MainActivity mainActivity){
		this.mainActivity = mainActivity;
	}

	@Override
	public void create () {
		loadAssets();
		gameScreen = new GameScreen(this, mainActivity);
		mainActivity.gameScreen = gameScreen;
		setScreen(gameScreen);
		SongStorage.load(GlobalVars.getInstance().songFirestore.objectID, mainActivity);
	}

	public void loadAssets() {
		InternalFileHandleResolver fileHandler = new InternalFileHandleResolver();
		assetManager = new AssetManager(fileHandler);
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(fileHandler));

		assetManager.load(MASTER_PATH, TextureAtlas.class);
		assetManager.finishLoading();
	}

	@Override
	public void dispose () {
		super.dispose();
		assetManager.dispose();
	}

	public AssetManager getAssetManager() {
        return assetManager;
	}

}
