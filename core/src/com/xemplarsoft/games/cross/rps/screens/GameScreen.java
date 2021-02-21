package com.xemplarsoft.games.cross.rps.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.utils.xwt.ScreenAdapter;

public class GameScreen extends ScreenAdapter{
    public static final float CAM_HEIGHT = 8F, CAM_WIDTH_MIN = 11F, CAM_WIDTH_MAX = 20F;
    public static long $_GLOBAL_CLOCK = 0L;
    public static float CAM_WIDTH;
    public static boolean runAd;

    public GameScreen(){
        cam = new OrthographicCamera(CAM_WIDTH_MIN, CAM_HEIGHT);
        vp = new ExtendViewport(CAM_WIDTH_MIN, CAM_HEIGHT, CAM_WIDTH_MAX, CAM_HEIGHT, cam);
        USE_HUD_UNITS = false;
    }

    public void renderSelf(float delta) {
        $_GLOBAL_CLOCK++;
        
        if(runAd){
            runAd = false;
            Wars.displayAd();
        }
    }

    public void resizeSelf(int width, int height) {
        CAM_WIDTH = (float) width/height * CAM_HEIGHT;

        vp.update(width, height, false);
        cam.position.set(CAM_WIDTH / 4F, CAM_HEIGHT / 2F, 0);
        cam.update();

        generateUI();
    }

    private void generateUI(){
        removeAllUI();

        
    }

    public void pause() {
        super.pause();
    }

    public void resume() {
        super.resume();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int x = (int)(Math.floor((float)screenX / screenWidth * CAM_WIDTH));
        int y = (int)Math.floor((float)(screenHeight - screenY) / screenHeight * CAM_HEIGHT);

        return super.touchDown(screenX, screenY, pointer, button);
    }
}
