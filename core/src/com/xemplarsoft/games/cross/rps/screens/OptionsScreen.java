package com.xemplarsoft.games.cross.rps.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xemplarsoft.utils.xwt.Panel;
import com.xemplarsoft.utils.xwt.ScreenAdapter;

public final class OptionsScreen extends ScreenAdapter {
    public static final float CAM_WIDTH = 18F, CAM_HEIGHT_MIN = 22F, CAM_HEIGHT_MAX = 40F;
    public static float CAM_HEIGHT;
    
    public OptionsScreen() {
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT_MAX);
        vp = new ExtendViewport(CAM_WIDTH, CAM_HEIGHT_MIN, CAM_WIDTH, CAM_HEIGHT_MAX, cam);
        USE_HUD_UNITS = false;
    }
    
    public void renderSelf(float delta) {
    
    }
    
    public void resizeSelf(int width, int height) {
        CAM_HEIGHT = (float) height/width * CAM_WIDTH;
        vp.update(width, height, false);
        cam.position.set(CAM_WIDTH / 2F, CAM_HEIGHT / 2F, 0);
        cam.update();
        removeAllUI();
        
        
        /*------------------------
         Pane Definitions
         ------------------------*/
        Panel main = new Panel(0, 0, CAM_WIDTH, CAM_HEIGHT, null, this);
    }
}
