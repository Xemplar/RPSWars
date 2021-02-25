package com.xemplarsoft.games.cross.rps.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.NoneAdProvider;

import javax.swing.*;

public class DesktopLauncher {

	public DesktopLauncher(){
	
	}

	private void launch(){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "RPS Wars";
		//config.addIcon("raw/app_icon_rounded.png", Files.FileType.Local);
		config.width = 400;
		config.height = 786;
		new LwjglApplication(new Wars(new NoneAdProvider()), config);
	}

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){
			e.printStackTrace();
		}
		DesktopLauncher l = new DesktopLauncher();
		l.launch();
	}
}
