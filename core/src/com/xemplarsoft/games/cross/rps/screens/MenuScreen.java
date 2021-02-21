package com.xemplarsoft.games.cross.rps.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.model.BounceBG;
import com.xemplarsoft.utils.xwt.*;
import com.xemplarsoft.utils.xwt.Action;

public class MenuScreen extends ScreenAdapter implements Action {
    public static final float CAM_HEIGHT = 8F, CAM_WIDTH_MIN = 11F, CAM_WIDTH_MAX = 20F;
    public static long $_GLOBAL_CLOCK = 0L;
    public static float CAM_WIDTH, STATS_WIDTH, STATS_OFFSET;

    //--------------------------------------
    // Main elements
    //--------------------------------------
    private Panel main;
    private ImageView title;
    private Button play, store, settings, credits;
    
    private final Wars wars;
    private BounceBG bg;

    public MenuScreen(Wars wars){
        this.wars = wars;

        cam = new OrthographicCamera(CAM_WIDTH_MIN, CAM_HEIGHT);
        vp = new ExtendViewport(CAM_WIDTH_MIN, CAM_HEIGHT, CAM_WIDTH_MAX, CAM_HEIGHT, cam);

        USE_HUD_UNITS = false;
    }

    public void renderSelf(float delta) {
        bg.update(delta);
        bg.render(batch);
    }

    public void resizeSelf(int width, int height) {
        CAM_WIDTH = (float) width/height * CAM_HEIGHT;

        STATS_WIDTH = (CAM_WIDTH - CAM_HEIGHT) / 2F;
        STATS_OFFSET = (CAM_WIDTH / 4F) - (CAM_HEIGHT / 2F);

        vp.update(width, height, false);
        cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2, 0);
        cam.update();
    
        this.bg = new BounceBG(Wars.ur("bg"), CAM_WIDTH * 3, CAM_WIDTH * 3);
        
        generateUI();
    }

    private void generateUI(){
        removeAllUI();
        generateMain();

        main.setVisible(true);
    }

    private void generateMain(){
        main = new Panel(0, 0, CAM_WIDTH, CAM_HEIGHT, null, this);

        final float button_width = 5;
        final float center_x = (CAM_WIDTH - button_width) / 2F;
        
        title = new ImageView(center_x - 5F, CAM_HEIGHT - 2F, 10F, 2F, Wars.ur("title"));

        main.setVisible(false);
        this.addToUI(main);
    }

    @Override
    public void pause() {
        main.destroy();
    }

    public void doAction(Button b, Type t) {
        if(t == Type.ON_DOWN){
        
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
