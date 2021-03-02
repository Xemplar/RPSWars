package com.xemplarsoft.games.cross.rps;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xemplarsoft.games.cross.rps.model.Team;
import com.xemplarsoft.games.cross.rps.screens.GameScreen;
import com.xemplarsoft.games.cross.rps.screens.MenuScreen;
import com.xemplarsoft.games.cross.rps.sprite.Sprite;
import com.xemplarsoft.games.cross.rps.sprite.SpriteA;

public class Wars extends Game {
	//public static final String TEST_DB_URL = "https://xemplarsoft.com/testdb/TestDBHook";
	public static final int A_ID = 1, B_ID = 2, C_ID = 3;
	
	public static final String APP_VERSION = "0.1.1";
	public static final String APP_ENCLAVE = "Alpha";
	public static final String APPCODE = "wars";
	public static final int PORT = 18765;

	private static TextureAtlas ui, pi;
	public static BitmapFont font, title, info;
	public static MenuScreen menu;
	public static GameScreen game;
	
	public static AdProvider ads;
	public static Sprite S_SCI, S_PAP, S_ROC;
	
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
		
		S_SCI = new SpriteA(0.3F, Wars.pr(Team.fromID(A_ID).prefix, 0), Wars.pr(Team.fromID(A_ID).prefix, 1));
		S_PAP = new Sprite(Wars.pr(Team.fromID(B_ID).prefix, 0));
		S_ROC = new Sprite(Wars.pr(Team.fromID(C_ID).prefix, 0));
	}

	private void loadScreens(){
		menu = new MenuScreen(this);
		game = new GameScreen();

		setScreen(game);
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
	public static TextureRegion pr(String name, int id) {
		TextureRegion ret = pi.findRegion(name, id);
		if(ret == null) System.out.println("Null Texture: " + name);
		return ret;
	}
}
