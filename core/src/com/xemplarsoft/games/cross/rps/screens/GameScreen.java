package com.xemplarsoft.games.cross.rps.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.model.*;
import com.xemplarsoft.utils.xwt.ScreenAdapter;

public class GameScreen extends ScreenAdapter{
    public static final float CAM_WIDTH = 18F, CAM_HEIGHT_MIN = 22F, CAM_HEIGHT_MAX = 36F;
    public static long $_GLOBAL_CLOCK = 0L;
    public static float CAM_HEIGHT;
    public static boolean runAd;
    public final World world;

    public GameScreen(){
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT_MAX);
        vp = new ExtendViewport(CAM_WIDTH, CAM_HEIGHT_MIN, CAM_WIDTH, CAM_HEIGHT_MAX, cam);
        USE_HUD_UNITS = false;
        
        world = new World();
        
    }

    public void renderSelf(float delta) {
        $_GLOBAL_CLOCK++;
        
        world.update(delta);
        world.render(batch);
        
        if(runAd){
            runAd = false;
            Wars.displayAd();
        }
    }

    public void resizeSelf(int width, int height) {
        CAM_HEIGHT = (float) height/width * CAM_WIDTH;

        vp.update(width, height, false);
        cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2F, 0);
        cam.update();
        world.clear();
    
        RandomXS128 ran = new RandomXS128();
    
        for(int i = 1; i < 4; i++){
            for(int j = 0; j < 100; j++){
                float x = ran.nextFloat() * CAM_WIDTH;
                float y = ran.nextFloat() * CAM_HEIGHT;
                Unit u = new BasicUnit(Team.fromID(i), x, y);
                world.spawn(u);
                u.setTarget(new Vector2(CAM_WIDTH / 2, CAM_HEIGHT / 2));
            }
        }

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

        for(Entity e : world.entities){
            if(e instanceof BasicUnit){
                e.setTarget(new Vector2(x, y));
            }
        }
        
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
