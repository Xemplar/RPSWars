package com.xemplarsoft.games.cross.rps;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xemplarsoft.games.cross.rps.model.Team;
import com.xemplarsoft.games.cross.rps.screens.*;
import com.xemplarsoft.games.cross.rps.sprite.Sprite;
import com.xemplarsoft.games.cross.rps.sprite.SpriteA;

import static com.xemplarsoft.games.cross.rps.screens.GameScreen.$_GLOBAL_CLOCK;

public class Wars extends Game {
	public static Wars instance;
	//public static final String TEST_DB_URL = "https://xemplarsoft.com/testdb/TestDBHook";
	public static final int A_ID = 1, B_ID = 2, C_ID = 3;
	
	public static final String APP_VERSION = "0.1.1";
	public static final String APP_ENCLAVE = "Alpha";
	public static final String APPCODE = "wars";
	public static final int PORT = 18765;

	private static TextureAtlas ui, pi;
	public static BitmapFont fnt_text, fnt_title, fnt_sub, fnt_button, fnt_splash;
	
	public static OptionsScreen scr_options;
	public static CreditsScreen scr_credits;
	public static SplashScreen scr_splash;
	public static MenuScreen scr_menu;
	public static GameScreen scr_game;
	public static TitleScreen scr_title;
	public static BuilderScreen scr_builder;
	public static boolean showing;
	
	public static AdProvider ads;
	public static Sprite S_SCI, S_PAP, S_ROC;
	
	public static Sound FX_SCI, FX_PAP, FX_ROC;
	public static Music mx_title;
	
	public Wars(AdProvider ads){
		Wars.instance = this;
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
		
		FX_SCI = loadSound(Team.fromID(A_ID).prefix);
		FX_PAP = loadSound(Team.fromID(B_ID).prefix);
		FX_ROC = loadSound(Team.fromID(C_ID).prefix);
		
		mx_title = loadMusic("title");
		mx_title.setLooping(true);
	}
	
	private static long SFX_DELAY = 40;
	private static long prevFXA = -SFX_DELAY, prevFXB = -SFX_DELAY, prevFXC = -SFX_DELAY;
	public static void playSound(Team t){
		switch(t.id){
			default: {
				if($_GLOBAL_CLOCK - prevFXA > SFX_DELAY) {
					prevFXA = $_GLOBAL_CLOCK;
					FX_SCI.play(1.0f);
				}
				break;
			}
			case B_ID: {
				if($_GLOBAL_CLOCK - prevFXB > SFX_DELAY){
					prevFXB = $_GLOBAL_CLOCK;
					FX_PAP.play(1.0f);
				}
				break;
			}
			case C_ID: {
				if($_GLOBAL_CLOCK - prevFXC > SFX_DELAY){
					prevFXC = $_GLOBAL_CLOCK;
					FX_ROC.play(1.0f);
				}
				break;
			}
		}
	}
	
	private Sound loadSound(String name){
		return Gdx.audio.newSound(Gdx.files.internal("audio/" + name + ".ogg"));
	}
	private Music loadMusic(String name){
		return Gdx.audio.newMusic(Gdx.files.internal("audio/" + name + ".ogg"));
	}

	private void loadScreens(){
		scr_splash = new SplashScreen();
		scr_menu = new MenuScreen(this);
		scr_game = new GameScreen();
		scr_title = new TitleScreen();
		scr_credits = new CreditsScreen();
		scr_options = new OptionsScreen();
		scr_builder = new BuilderScreen();

		setScreen(scr_splash);
	}

	private void loadFonts(){
		fnt_text = new BitmapFont(Gdx.files.internal("fonts/melon.fnt"));
		fnt_text.setUseIntegerPositions(false);
		fnt_text.getData().setScale(0.01125F);
		fnt_text.setColor(Color.WHITE);
		
		fnt_splash = new BitmapFont(Gdx.files.internal("fonts/melon.fnt"));
		fnt_splash.setUseIntegerPositions(false);
		fnt_splash.getData().setScale(0.02F);
		fnt_splash.setColor(Color.BLACK);
		
		fnt_title = new BitmapFont(Gdx.files.internal("fonts/abs.fnt"));
		fnt_title.setUseIntegerPositions(false);
		fnt_title.getData().setScale(0.02F);
		fnt_title.setColor(Color.BLACK);
		
		fnt_button = new BitmapFont(Gdx.files.internal("fonts/abs.fnt"));
		fnt_button.setUseIntegerPositions(false);
		fnt_button.getData().setScale(0.015F);
		fnt_button.setColor(Color.WHITE);
		
		fnt_sub = new BitmapFont(Gdx.files.internal("fonts/abs.fnt"));
		fnt_sub.setUseIntegerPositions(false);
		fnt_sub.getData().setScale(0.01F);
		fnt_sub.setColor(Color.WHITE);
	}

	public void dispose () {

	}

	public static void displayAd(){
		ads.displayAd();
	}
	public static void displayBanner(boolean display){
		showing = display;
		ads.displayBannerAd(display);
	}
	
	public static TextureRegion ur(String name) {
		TextureRegion ret = ui.findRegion(name);
		if(ret == null) System.out.println("Null Texture: " + name);
		return ret;
	}
	public static TextureRegion ur(String name, int id) {
		TextureRegion ret = ui.findRegion(name, id);
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
