package com.xemplarsoft.games.cross.rps.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.NoneAdProvider;

import javax.swing.*;
import java.awt.*;

public class DesktopLauncher {

	public DesktopLauncher(){
	
	}

	private void launch(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "RPS Wars";
		//config.addIcon("raw/app_icon_rounded.png", Files.FileType.Local);
		config.width = 432;
		config.height = 768;
		config.x = (screenSize.width - config.width) / 2;
		config.y = (screenSize.height - config.height) / 2;
		config.undecorated = true;
		config.resizable = false;
		config.samples = 3;
		
		LwjglApplication l = new LwjglApplication(new Wars(new NoneAdProvider()), config);
	}

	public static void main(String[] args){
		try{
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){
			e.printStackTrace();
		}
		DesktopLauncher l = new DesktopLauncher();
		l.launch();
	}
}
