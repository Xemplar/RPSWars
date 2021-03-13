package com.xemplarsoft.games.cross.rps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.utils.xwt.*;
import com.xemplarsoft.utils.xwt.settings.AbstractSetting;
import com.xemplarsoft.utils.xwt.settings.CheckboxSetting;
import com.xemplarsoft.utils.xwt.settings.NumberSetting;
import com.xemplarsoft.utils.xwt.settings.SettingChangeListener;

import java.nio.charset.StandardCharsets;

public final class OptionsScreen extends ScreenAdapter implements Action, SettingChangeListener {
    public static final String prefLocation = "prefs";
    public static final float CAM_WIDTH = 18F, CAM_HEIGHT_MIN = 22F, CAM_HEIGHT_MAX = 40F;
    public static float CAM_HEIGHT;
    
    public static String[] prefs;
    
    public CheckboxSetting soundfx, soundbg, convert;
    public NumberSetting units;
    public SegmentedButton title;
    public Button back;
    
    public FadeView fadeIn, fadeOut;
    
    public OptionsScreen() {
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT_MAX);
        vp = new ExtendViewport(CAM_WIDTH, CAM_HEIGHT_MIN, CAM_WIDTH, CAM_HEIGHT_MAX, cam);
        USE_HUD_UNITS = false;
        FileHandle handle = Gdx.files.local(prefLocation);
        if(!handle.exists()){
            handle.writeBytes("true,true,10,true".getBytes(StandardCharsets.UTF_8), false);
        }
        
        prefs = versionSave(new String(handle.readBytes(), StandardCharsets.UTF_8).split(","));
    }
    
    private static final int PREF_COUNT = 4;
    private String[] versionSave(String[] content){
        String[] n = new String[PREF_COUNT];
        System.arraycopy(content, 0, n, 0, content.length);
        if(content.length < PREF_COUNT){
            //if(content.length < 5)
            if(content.length < 4) n = "true,true,10,true".split(",");
        }
        
        return n;
    }
    
    public static String getVal(int index){
        return prefs[index];
    }
    
    public static boolean getBoolean(int index){
        return Boolean.parseBoolean(prefs[index]);
    }
    
    public static int getInt(int index){
        return Integer.parseInt(prefs[index]);
    }
    
    public void renderSelf(float delta) {
        if(fadeIn != null) fadeIn.update(delta);
        if(fadeOut != null) fadeOut.update(delta);
    }
    
    public void resizeSelf(int width, int height) {
        CAM_HEIGHT = (float) height/width * CAM_WIDTH;
        vp.update(width, height, false);
        cam.position.set(CAM_WIDTH / 2F, CAM_HEIGHT / 2F, 0);
        cam.update();
        removeAllUI();
        
        fadeIn = new FadeView(CAM_WIDTH, CAM_HEIGHT, Wars.ur("fade_black"), true);
        fadeOut = new FadeView(CAM_WIDTH, CAM_HEIGHT, Wars.ur("fade_black"), false);
        
        fadeIn.setAction(new Action() {
            public void doAction(Button b, Type t) {
                Wars.instance.setScreen(Wars.scr_title);
            }
        });
        if(!fadeOut.isRunning()) fadeOut.start();
        
        SegmentedPanel main = new SegmentedPanel(0, CAM_HEIGHT - (CAM_WIDTH / 16), CAM_WIDTH, CAM_HEIGHT - (CAM_WIDTH / 16), this);
        main.setTextures("pnl_stone");
        main.setBack("paper");
        
        title = new SegmentedButton(2F, CAM_HEIGHT, CAM_WIDTH - 4F, CAM_WIDTH / 8, "Options");
        title.setOffY(-0.25F);
        title.setTextures("lbl_stone");
        title.setFont(Wars.fnt_button);
        title.setAlignment(Align.center);
    
        back = new Button(CAM_WIDTH / 16 * 2, CAM_WIDTH / 16 * 4, CAM_WIDTH / 8, CAM_WIDTH / 8, "");
        back.setBG(Wars.ur("close"));
        back.setAction(new Action() {
            public void doAction(Button b, Type t) {
                if(b == back && t.equals(Type.CLICKED)){
                    fadeIn.start();
                }
            }
        });
        
        soundfx = new CheckboxSetting("Sound FX", "Enable in game sound fx.", CAM_WIDTH, CAM_HEIGHT - (CAM_WIDTH / 4) - (CAM_WIDTH / 16 * 3), this);
        soundbg = new CheckboxSetting("BG Music", "Enable game background music.", CAM_WIDTH, CAM_HEIGHT - (CAM_WIDTH / 4 * 2) - (CAM_WIDTH / 16 * 3), this);
        convert = new CheckboxSetting("Convert Unit", "If checked, units are converted. If unchecked, units are killed.", CAM_WIDTH, CAM_HEIGHT - (CAM_WIDTH / 4 * 4) - (CAM_WIDTH / 16 * 4), this);
        units = new NumberSetting("Units", "Amount of units per team.\n[5 - 25]", CAM_WIDTH, CAM_HEIGHT - (CAM_WIDTH / 4 * 3) - (CAM_WIDTH / 16 * 3), this);
        units.setBounds(5, 25);
        
        soundfx.setData(Boolean.parseBoolean(prefs[0]));
        soundbg.setData(Boolean.parseBoolean(prefs[1]));
        units.setData(Integer.parseInt(prefs[2]));
        convert.setData(Boolean.parseBoolean(prefs[3]));
        
        soundfx.setListener(this);
        soundbg.setListener(this);
        units.setListener(this);
        convert.setListener(this);
    
        addToUI(title);
        main.addView(back);
        main.addView(soundfx);
        main.addView(soundbg);
        main.addView(convert);
        main.addView(units);
        addToUI(main);
        addToUI(fadeIn);
        addToUI(fadeOut);
    }
    
    public void changed(AbstractSetting<?> setting, String value) {
        boolean save = false;
        if(setting == soundfx){
            save = true;
            prefs[0] = value;
        }
        if(setting == soundbg){
            save = true;
            prefs[1] = value;
            if(Boolean.parseBoolean(value)) Wars.mx_title.play();
            else Wars.mx_title.pause();
        }
        if(setting == units){
            save = true;
            prefs[2] = value;
        }
        if(setting == convert){
            save = true;
            prefs[3] = value;
        }
        
        if(save) saveSettings();
    }
    
    private void saveSettings(){
        StringBuilder b = new StringBuilder();
        for(String s : prefs){
            b.append(',').append(s);
        }
        String fin = b.substring(1);
        System.out.println(fin);
        byte[] data = fin.getBytes(StandardCharsets.UTF_8);
        Gdx.files.local(prefLocation).writeBytes(data, false);
    }
    
    @Override
    public void doAction(Button b, Type t) {
        if(b == back && t == Type.CLICKED){
            Wars.instance.setScreen(Wars.scr_title);
        }
    }
}
