package com.xemplarsoft.games.cross.rps.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.NoneAdProvider;

public class HtmlLauncher extends GwtApplication implements HTMLLoginListener{
    public GwtApplicationConfiguration getConfig () {
        return new GwtApplicationConfiguration(512, 256);
    }

    public ApplicationListener createApplicationListener () {
        HTMLTestDBApplication testdb = new HTMLTestDBApplication(this);
        testdb.login();

        return new Wars(testdb, testdb.getTestDB(), new NoneAdProvider(), null);
    }

    public Preloader.PreloaderCallback getPreloaderCallback() {
        return createPreloaderPanel(GWT.getHostPageBaseURL() + "preload.png");
    }

    protected void adjustMeterPanel(Panel meterPanel, Style meterStyle) {
        meterPanel.setStyleName("gdx-meter");
        meterPanel.addStyleName("nostripes");
        meterStyle.setProperty("backgroundColor", "#ffffff");
        meterStyle.setProperty("backgroundImage", "none");
    }

    public void onModuleLoad () {
        super.onModuleLoad();
        Window.addResizeHandler(new ResizeHandler() {
            public void onResize(ResizeEvent ev) {
                final int width = ev.getWidth();
                final int height = ev.getHeight();
                getCanvasElement().setWidth(width);
                getCanvasElement().setHeight(height);
                Gdx.app.postRunnable(new Runnable() {
                    public void run() {
                        Gdx.app.getApplicationListener().resize(width, height);
                    }
                });
            }
        });
    }

    @Override
    public void loggedIn(boolean worked, String message) {
        //TODO Implement login
    }
}