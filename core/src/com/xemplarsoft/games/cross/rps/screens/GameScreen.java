package com.xemplarsoft.games.cross.rps.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.controller.ai.UnitAI;
import com.xemplarsoft.games.cross.rps.model.*;
import com.xemplarsoft.games.cross.rps.model.unit.BasicUnit;
import com.xemplarsoft.games.cross.rps.model.unit.Unit;
import com.xemplarsoft.utils.xwt.*;

public class GameScreen extends ScreenAdapter{
    public static final float CAM_WIDTH = 18F, CAM_HEIGHT_MIN = 32F, CAM_HEIGHT_MAX = 40F;
    public static long $_GLOBAL_CLOCK = 0L;
    public static float CAM_HEIGHT;
    public static boolean runAd;
    public final World world;
    public final ShapeRenderer debug;
    public static Rectangle playArea;

    public GameScreen(){
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT_MAX);
        vp = new ExtendViewport(CAM_WIDTH, CAM_HEIGHT_MIN, CAM_WIDTH, CAM_HEIGHT_MAX, cam);
        USE_HUD_UNITS = false;
        
        world = new World();
        debug = new ShapeRenderer();
    }

    public void renderSelf(float delta) {
        $_GLOBAL_CLOCK++;
        batch.end();
        
        debug.setProjectionMatrix(cam.combined);
        debug.begin(ShapeRenderer.ShapeType.Line);
        world.update(delta, debug);
        debug.end();
        
        batch.begin();
        world.render(batch);
        
        if(runAd){
            runAd = false;
            Wars.displayAd();
        }
    }

    public void resizeSelf(int width, int height) {
        CAM_HEIGHT = (float) height/width * CAM_WIDTH;
        playArea = new Rectangle(1F, 3F, CAM_WIDTH - 2F, CAM_HEIGHT - 4F);

        vp.update(width, height, false);
        cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2F, 0);
        cam.update();
        
        restart();
        
        Wars.mx_title.pause();
        generateUI();
        Wars.displayBanner(true);
    }
    
    private void restart(){
        world.clear();
    
        RandomXS128 ran = new RandomXS128();
    
        for(int i = 1; i < 4; i++){
            Team t = Team.fromID(i);
            Entity:
            for(int j = 0; j < Wars.PARAMS.getUnitCount(t); j++){
                float x = ran.nextFloat() * playArea.width + playArea.x;
                float y = ran.nextFloat() * playArea.height + playArea.y;
            
                Unit u = new BasicUnit(t, x, y);
                u.setAI(new UnitAI());
                for(Entity e : world.entities){
                    if(u.getBounds().overlaps(e.getBounds())){
                        j--;
                        continue Entity;
                    }
                }
                world.spawn(u);
            }
        }
    }
    
    private void generateUI(){
        removeAllUI();
        Panel main = new Panel(0, 0, CAM_WIDTH, 3F, null, this);
        final Button back = new Button(0.5F, 0.5F, 2F, 2F, "");
        final Button restart = new Button(3.0F, 0.5F, 2F, 2F, "");
        
        back.setBG(Wars.ur("back"));
        restart.setBG(Wars.ur("restart"));
    
        back.setAction(new Action() {
            public void doAction(Button b, Type t) {
                if(b == back && t == Type.CLICKED) Wars.instance.setScreen(Wars.scr_title);
            }
        });
        restart.setAction(new Action() {
            public void doAction(Button b, Type t) {
                if(b == restart && t == Type.CLICKED) restart();
            }
        });
        
        main.addView(back);
        main.addView(restart);
        main.setPosition(0F, 2F);
        addToUI(main);
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
    
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        int x = (int)(Math.floor((float)screenX / screenWidth * CAM_WIDTH));
        int y = (int)Math.floor((float)(screenHeight - screenY) / screenHeight * CAM_HEIGHT);
        
        return super.touchDragged(screenX, screenY, pointer);
    }
}
