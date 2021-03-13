package com.xemplarsoft.games.cross.rps.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.utils.xwt.*;

public class SplashScreen extends ScreenAdapter {
    public static final float CAM_WIDTH = 18F, CAM_HEIGHT_MIN = 22F, CAM_HEIGHT_MAX = 40F;
    public static float CAM_HEIGHT;
    
    public ImageView logo;
    public Label title;
    
    public FadeView fadeIn;
    
    public SplashScreen() {
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT_MAX);
        vp = new ExtendViewport(CAM_WIDTH, CAM_HEIGHT_MIN, CAM_WIDTH, CAM_HEIGHT_MAX, cam);
        clear = new Color(1, 1, 1, 1);
    }
    
    protected float trans = 0, counter = 0;
    public void renderSelf(float delta) {
        logo.setTransparency(trans);
        if(fadeIn != null) fadeIn.update(delta);
        if(logo != null){
            counter += delta;
            if(trans < 1F) trans += delta / 0.75;
            if(trans > 1F) trans = 1F;
            
            if(counter >= 1.5F){
                fadeIn.start();
            }
        }
    }
    
    public void resizeSelf(int width, int height) {
        CAM_HEIGHT = (float) height/width * CAM_WIDTH;
        vp.update(width, height, false);
        cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2F, 0);
        cam.update();
        
        removeAllUI();
        fadeIn = new FadeView(CAM_WIDTH, CAM_HEIGHT, Wars.ur("fade_black"), true);
        fadeIn.setAction(new Action() {
            public void doAction(Button b, Type t) {
                Wars.instance.setScreen(Wars.scr_title);
            }
        });
        
        logo = new ImageView(3F, (CAM_HEIGHT - (CAM_WIDTH - 6F)) / 2F + 1.5F, CAM_WIDTH - 6F, CAM_WIDTH - 6F, Wars.ur("logo"));
        title = new Label(1F, (CAM_HEIGHT - (CAM_WIDTH - 6F)) / 2F - 1.5F, CAM_WIDTH - 2F, 2F, "Xemplar Softworks, LLC");
        title.setAlignment(Align.center);
        title.setFont(Wars.fnt_splash);
        addToUI(logo);
        addToUI(title);
        addToUI(fadeIn);
    }
}