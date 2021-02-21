package com.xemplarsoft.games.cross.rps;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xemplarsoft.games.cross.rps.screens.GameScreen;
import com.xemplarsoft.games.cross.rps.screens.MenuScreen;

public class Wars extends Game {
	//public static final String TEST_DB_URL = "https://xemplarsoft.com/testdb/TestDBHook";
	public static final String APP_VERSION = "0.1.1";
	public static final String APP_ENCLAVE = "Alpha";
	public static final String APPCODE = "wars";
	public static final int PORT = 18765;

	private static TextureAtlas ui, pi;
	public static BitmapFont font, title, info;
	public static MenuScreen menu;
	public static GameScreen game;
	
	public static AdProvider ads;
	
	public Wars(AdProvider ads){
		Wars.ads = ads;
	}

	public void create () {
		loadAssets();
		loadFonts();
		loadScreens();
	}

	private void loadAssets(){
		ui = new TextureAtlas(Gdx.files.internal("textures/ui.atlas"));
		pi = new TextureAtlas(Gdx.files.internal("textures/piece.atlas"));
	}

	private void loadScreens(){
		menu = new MenuScreen(this);
		game = new GameScreen();

		setScreen(menu);
	}

	private void loadFonts(){
		font = new BitmapFont(Gdx.files.internal("fonts/museo.fnt"));
		font.setUseIntegerPositions(false);
		font.getData().setScale(0.0075F);
		font.setColor(Color.BLACK);

		title = new BitmapFont(Gdx.files.internal("fonts/museo.fnt"));
		title.setUseIntegerPositions(false);
		title.getData().setScale(0.01F);

		info = new BitmapFont(Gdx.files.internal("fonts/museo.fnt"));
		info.setUseIntegerPositions(false);
		info.getData().setScale(0.0040F);
	}

	public void dispose () {

	}

	public static void displayAd(){
		ads.displayAd();
	}

	public static TextureRegion ur(String name) {
		TextureRegion ret = ui.findRegion(name);
		if(ret == null) System.out.println("Null Texture: " + name);
		return ret;
	}
	public static TextureRegion pr(String name) {
		TextureRegion ret = pi.findRegion(name);
		if(ret == null) System.out.println("Null Texture: " + name);
		return ret;
	}
}
