package com.xemplarsoft.games.cross.rps.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.model.BounceBG;
import com.xemplarsoft.utils.xwt.*;
import com.xemplarsoft.utils.xwt.Action;

public class MenuScreen extends ScreenAdapter implements Action {
    public static final float CAM_HEIGHT = 8F, CAM_WIDTH_MIN = 11F, CAM_WIDTH_MAX = 20F;
    public static float CAM_WIDTH;

    //--------------------------------------
    // Main elements
    //--------------------------------------
    private Panel main;

    public MenuScreen(){
        cam = new OrthographicCamera(CAM_WIDTH_MIN, CAM_HEIGHT);
        vp = new ExtendViewport(CAM_WIDTH_MIN, CAM_HEIGHT, CAM_WIDTH_MAX, CAM_HEIGHT, cam);

        USE_HUD_UNITS = false;
    }

    public void renderSelf(float delta) {
    
    }

    public void resizeSelf(int width, int height) {
        CAM_WIDTH = (float) width/height * CAM_HEIGHT;

        vp.update(width, height, false);
        cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2, 0);
        cam.update();
        
        generateUI();
    }

    private void generateUI(){
        removeAllUI();
        generateMain();

        main.setVisible(true);
    }

    private void generateMain(){
        main = new Panel(0, 0, CAM_WIDTH, CAM_HEIGHT, null, this);

        main.setVisible(false);
        this.addToUI(main);
    }
    
    public void pause() {
        main.destroy();
    }

    public void doAction(Button b, Type t) {
        if(t == Type.ON_DOWN){
        
        }
    }
}
