package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xemplarsoft.games.cross.rps.Wars;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public abstract class ScreenAdapter implements Screen, InputProcessor, ComponentHandler {
    public static boolean USE_HUD_UNITS = true;
    public static final int HUD_WIDTH = 256;
    public static float HUD_HEIGHT;

    protected Array<AbstractElement> ui = new Array<AbstractElement>();
    protected Array<Interfacable> interact = new Array<Interfacable>();

    protected SpriteBatch batch;
    protected Color clear = new Color(0.1F, 0.1F, 0.1F, 1);
    public static float WORLD_ASPECT;
    protected Camera cam, hud;
    protected ExtendViewport vp;
    protected int screenWidth, screenHeight;

    public ScreenAdapter(){
        batch = new SpriteBatch();
    }

    public abstract void renderSelf(float delta);
    public abstract void resizeSelf(int width, int height);
    public void render(float delta){
        Gdx.gl.glClearColor(clear.r, clear.g, clear.b, clear.a);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        renderSelf(delta);

        if(USE_HUD_UNITS) {
            batch.end();
            batch.setProjectionMatrix(hud.combined);
            batch.begin();
        }

        for(int i = 0; i < ui.size; i++){
            AbstractElement comp = ui.get(i);
            if(comp.isVisible()){
                comp.render(batch);
            }
        }
        batch.end();
    }

    public void addToUI(AbstractElement component) {
        addUI(component);
    }

    public void removeFromUI(AbstractElement component) {
        removeUI(component);
    }

    public void addUI(AbstractElement comp){
        if(ui.contains(comp, true)) return;

        ui.add(comp);
        if(comp instanceof Interfacable){
            interact.add((Interfacable) comp);
        }
    }
    public void removeUI(AbstractElement comp){
        ui.removeValue(comp, false);
        if(comp instanceof Interfacable){
            interact.removeValue((Interfacable) comp, false);
        }
    }
    public void removeAllUI(){
        ui.clear();
        interact.clear();
    }
    public void resize(int width, int height){
        screenWidth = width;
        screenHeight = height;

        WORLD_ASPECT = (float)height / (float)width;
        HUD_HEIGHT = HUD_WIDTH * WORLD_ASPECT;

        hud = new OrthographicCamera(HUD_WIDTH, HUD_WIDTH * WORLD_ASPECT);
        hud.position.set(HUD_WIDTH / 2, HUD_WIDTH * WORLD_ASPECT / 2 , 0);
        hud.update();

        resizeSelf(width, height);
    }
    public void show() {
        Gdx.input.setInputProcessor(this);
    }
    public void pause() {
        Gdx.input.setInputProcessor(null);
    }
    public void resume() {
        Gdx.input.setInputProcessor(this);
    }
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 worldCoords = hud.unproject(new Vector3(screenX, screenY, 0));
        float worldX = worldCoords.x, worldY = worldCoords.y;

        boolean ret = false;
        for(Interfacable interfacable : interact){
            if(!interfacable.isVisible()) continue;
            ret |= interfacable.isMovedOver(worldX, worldY);
        }
        return ret;
    }
    public boolean scrolled(int amount) {
        return false;
    }
    public boolean keyTyped(char character) {
        return false;
    }
    public boolean keyUp(int keycode) {
        return false;
    }
    public boolean keyDown(int keycode) {
        return false;
    }
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldCoords = USE_HUD_UNITS ? hud.unproject(new Vector3(screenX, screenY, 0)) : cam.unproject(new Vector3(screenX, screenY, 0));
        float worldX = worldCoords.x, worldY = worldCoords.y;

        boolean ret = false;
        for(Interfacable interfacable : interact){
            if(!interfacable.isVisible()) continue;
            ret |= interfacable.isTouchedDown(worldX, worldY, pointer, button);
        }
        return ret;
    }
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 worldCoords = USE_HUD_UNITS ? hud.unproject(new Vector3(screenX, screenY, 0)) : cam.unproject(new Vector3(screenX, screenY, 0));
        float worldX = worldCoords.x, worldY = worldCoords.y;

        boolean ret = false;
        for(Interfacable interfacable : interact){
            if(!interfacable.isVisible()) continue;
            ret |= interfacable.isTouchedUp(worldX, worldY, pointer, button);
        }
        return ret;
    }
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 worldCoords = USE_HUD_UNITS ? hud.unproject(new Vector3(screenX, screenY, 0)) : cam.unproject(new Vector3(screenX, screenY, 0));
        float worldX = worldCoords.x, worldY = worldCoords.y;

        boolean ret = false;
        for(Interfacable interfacable : interact){
            if(!interfacable.isVisible()) continue;
            ret |= interfacable.isDraggedOn(worldX, worldY, pointer);
        }
        return ret;
    }

    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
