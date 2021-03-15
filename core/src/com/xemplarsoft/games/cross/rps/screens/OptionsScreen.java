package com.xemplarsoft.games.cross.rps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.scenario.Parameters;
import com.xemplarsoft.utils.Base64;
import com.xemplarsoft.utils.xwt.*;
import com.xemplarsoft.utils.xwt.settings.*;
import com.xemplarsoft.utils.xwt.settings.MultiNumberSetting.NamedValue;

import java.nio.charset.StandardCharsets;

public final class OptionsScreen extends ScreenAdapter implements Action, SettingChangeListener {
    public static final String prefLocation = "prefs";
    public static final float CAM_WIDTH = 18F, CAM_HEIGHT_MIN = 32F, CAM_HEIGHT_MAX = 40F;
    public static float CAM_HEIGHT;
    
    public static String[] prefs;
    
    public CheckboxSetting soundfx, soundbg;
    public SegmentedButton title;
    public Button back;
    public SegmentedPanel main;
    
    //**********************************
    //* Params Config UI
    //**********************************
    public CategorySetting cat_params;
    public SegmentedPanel pnl_params;
    public NumberSetting num_units;
    public CheckboxSetting cb_convert;
    public DecimalSetting dec_base, dec_chase, dec_run;
    
    //**********************************
    //* Team Config UI
    //**********************************
    public CategorySetting cat_team;
    public SegmentedPanel pnl_team;
    public MultiNumberSetting stat_rock, stat_paper, stat_scissors;
    
    public FadeView fadeIn, fadeOut;
    
    public OptionsScreen() {
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT_MAX);
        vp = new ExtendViewport(CAM_WIDTH, CAM_HEIGHT_MIN, CAM_WIDTH, CAM_HEIGHT_MAX, cam);
        USE_HUD_UNITS = false;
        FileHandle handle = Gdx.files.local(prefLocation);
        if(!handle.exists()){
            handle.writeBytes("true,true,,".getBytes(StandardCharsets.UTF_8), false);
        }
        
        prefs = versionSave(new String(handle.readBytes(), StandardCharsets.UTF_8).split(","));
    }
    
    public void loadParameters(Parameters p){
        String[] data_params = prefs[2].split("#");
        for(int i = 0; i < data_params.length; i++){
            data_params[i] = Base64.decodeToString(data_params[i]);
        }
        try {
            if(data_params.length > 0) p.sta_paper.units = p.sta_rock.units = p.sta_scissor.units = Integer.parseInt(data_params[0]);
        } catch (NumberFormatException e){
            p.sta_paper.units = p.sta_rock.units = p.sta_scissor.units = 1;
        }
        p.sta_paper.attack = p.sta_rock.attack = p.sta_scissor.attack = 1;
        p.sta_paper.defense = p.sta_rock.defense = p.sta_scissor.defense = 0;
        try {
            if(data_params.length > 1) p.chase_speed = Float.parseFloat(data_params[1]);
        } catch (NumberFormatException e){
            p.chase_speed = p.sta_scissor.units = 1;
        }
        try {
            if(data_params.length > 2) p.run_speed = Float.parseFloat(data_params[2]);
        } catch (NumberFormatException e){
            p.run_speed = 1;
        }
    
        if(data_params.length > 3) p.convert = Boolean.parseBoolean(data_params[3]);
    
        String[] data_unit = prefs[3].split("#");
        for(int i = 0; i < data_unit.length; i++){
            data_unit[i] = Base64.decodeToString(data_unit[i]);
        }
        
        String[] p_r = data_unit[0].split("#");
        String[] p_p = data_unit[1].split("#");
        String[] p_s = data_unit[2].split("#");
    
        p.sta_rock.hp      = Integer.parseInt(p_r[0]);
        p.sta_rock.attack  = Integer.parseInt(p_r[1]);
        p.sta_rock.defense = Integer.parseInt(p_r[2]);
        p.sta_rock.speed   = Integer.parseInt(p_r[3]) * 0.1F;
    
        p.sta_paper.hp      = Integer.parseInt(p_p[0]);
        p.sta_paper.attack  = Integer.parseInt(p_p[1]);
        p.sta_paper.defense = Integer.parseInt(p_p[2]);
        p.sta_paper.speed   = Integer.parseInt(p_p[3]) * 0.1F;
    
        p.sta_scissor.hp      = Integer.parseInt(p_s[0]);
        p.sta_scissor.attack  = Integer.parseInt(p_s[1]);
        p.sta_scissor.defense = Integer.parseInt(p_s[2]);
        p.sta_scissor.speed   = Integer.parseInt(p_s[3]) * 0.1F;
    }
    
    private static final int PREF_COUNT = 5;
    private String[] versionSave(String[] content){
        String[] n = new String[PREF_COUNT];
        System.arraycopy(content, 0, n, 0, Math.min(content.length, PREF_COUNT));
        if(content.length != PREF_COUNT){
            n = ("false,false,MTA#MS4w#MS4w#MA,MSMxIzAjMTA#MSMxIzAjMTA#MSMxIzAjMTA,").split(",");
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
        boolean menuOpen = pnl_params.isVisible() || pnl_team.isVisible();
        if(menuOpen) main.hideAll(); else main.showAll();
        
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
        
        main = new SegmentedPanel(0, CAM_HEIGHT - (CAM_WIDTH / 16), CAM_WIDTH, CAM_HEIGHT - (CAM_WIDTH / 16), this);
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
        cat_params = new CategorySetting("Game Params", "Edit the game parameters like speed.", CAM_WIDTH, CAM_HEIGHT - (CAM_WIDTH / 4 * 3) - (CAM_WIDTH / 16 * 3), this);
        cat_team = new CategorySetting("Team Stats", "Edit unit stats like attack and defense.", CAM_WIDTH, CAM_HEIGHT - (CAM_WIDTH / 4 * 4) - (CAM_WIDTH / 16 * 3), this);
        
        soundfx.setListener(this);
        soundbg.setListener(this);
        cat_params.setListener(this);
        cat_team.setListener(this);
    
        loadSettings();
        generateParams();
        generateTeams();
        
        addToUI(title);
        main.addView(back);
        main.addView(soundfx);
        main.addView(soundbg);
        main.addView(cat_params);
        main.addView(cat_team);
        addToUI(main);
        
        registerCategories();
        
        addToUI(fadeIn);
        addToUI(fadeOut);
    }
    
    private void loadSettings(){
        soundfx.setData(prefs[0]);
        soundbg.setData(prefs[1]);
        cat_params.setData(prefs[2]);
        cat_team.setData(prefs[3]);
    }
    
    private void generateParams(){
        final float paramsWidth = CAM_WIDTH;
        final float settingHeight = paramsWidth / 4;
        final float paramsPadding = CAM_WIDTH / 16F;
        final float paramsHeight = settingHeight * 3F + paramsPadding * 5F * 2F;
        pnl_params = new SegmentedPanel(0, (CAM_HEIGHT - paramsHeight) / 2F + paramsHeight, paramsWidth - paramsPadding * 2, paramsHeight, this);
        pnl_params.setTextures("pnl_stone");
        pnl_params.setBack("paper");
        
        Vector2 pos = pnl_params.getPosition();
    
        num_units = new NumberSetting("Unit Count", "Number of units each team gets.", pnl_params.getWidth(), pos.y - (paramsPadding * 2 + settingHeight), this);
        num_units.setBounds(5, 30);
    
        dec_chase = new DecimalSetting("Chase Speed", "Multiplier for entities speed when chasing.", pnl_params.getWidth(), pos.y - (paramsPadding * 2 + settingHeight * 2), this);
        dec_chase.setBounds(0.1F, 3F, 0.1F);
    
        dec_run = new DecimalSetting("Run Speed", "Multiplier for entities speed when running.", pnl_params.getWidth(), pos.y - (paramsPadding * 2 + settingHeight * 3), this);
        dec_run.setBounds(0.1F, 3F, 0.1F);
    
        cb_convert = new CheckboxSetting("Convert Unit", "Convert enemy unit instead of attacking.", pnl_params.getWidth(), pos.y - (paramsPadding * 2 + settingHeight * 4), this);
    }
    private void generateTeams(){
        final float paramsWidth = CAM_WIDTH;
        final float settingHeight = paramsWidth / 4;
        final float paramsPadding = CAM_WIDTH / 16F;
        final float paramsHeight = (settingHeight + 2F) * 3F + paramsPadding * 4F;
        pnl_team = new SegmentedPanel(0, (CAM_HEIGHT - paramsHeight) / 2F + paramsHeight, paramsWidth - paramsPadding * 2, paramsHeight, this);
        pnl_team.setTextures("pnl_stone");
        pnl_team.setBack("paper");
        
        Vector2 pos = pnl_team.getPosition();
        final float multiHeight = pnl_team.getWidth() / 4F + 1F;
    
        stat_rock = new MultiNumberSetting("Rock", pnl_team.getWidth(), pos.y - (paramsPadding * 4 + settingHeight) - (multiHeight) * 0.5F, pnl_team);
        NamedValue h_r = NamedValue.ni("Health", "1", true); h_r.setBounds(20, 1, 1);
        NamedValue a_r = NamedValue.ni("Attack", "1", true); a_r.setBounds(20, 1, 1);
        NamedValue d_r = NamedValue.ni("Defense", "0", true); d_r.setBounds(20, 0, 1);
        NamedValue s_r = NamedValue.ni("Speed", "1", false); s_r.setBounds(3F, 0.1F, 0.1F);
        stat_rock.setValues(h_r, a_r, d_r, s_r);
        stat_rock.setIcons("stats");
        stat_rock.initializeSpinners();
    
        stat_paper = new MultiNumberSetting("Paper", pnl_team.getWidth(), pos.y - (paramsPadding * 4 + settingHeight) - (multiHeight) * 1.5F, pnl_team);
        NamedValue h_p = NamedValue.ni("Health", "1", true); h_p.setBounds(20, 1, 1);
        NamedValue a_p = NamedValue.ni("Attack", "1", true); a_p.setBounds(20, 1, 1);
        NamedValue d_p = NamedValue.ni("Defense", "0", true); d_p.setBounds(20, 0, 1);
        NamedValue s_p = NamedValue.ni("Speed", "1", false); s_p.setBounds(3F, 0.1F, 0.1F);
        stat_paper.setValues(h_p, a_p, d_p, s_p);
        stat_paper.initializeSpinners();
    
        stat_scissors = new MultiNumberSetting("Scissors", pnl_team.getWidth(), pos.y - (paramsPadding * 4 + settingHeight) - (multiHeight) * 2.5F, pnl_team);
        NamedValue h_s = NamedValue.ni("Health", "1", true); h_s.setBounds(20, 1, 1);
        NamedValue a_s = NamedValue.ni("Attack", "1", true); a_s.setBounds(20, 1, 1);
        NamedValue d_s = NamedValue.ni("Defense", "0", true); d_s.setBounds(20, 0, 1);
        NamedValue s_s = NamedValue.ni("Speed", "1", false); s_s.setBounds(3F, 0.1F, 0.1F);
        stat_scissors.setValues(h_s, a_s, d_s, s_s);
        stat_scissors.initializeSpinners();
    }
    
    private void registerCategories(){
        cat_params.registerPanel(pnl_params);
        cat_params.addSetting(num_units);
        cat_params.addSetting(dec_chase);
        cat_params.addSetting(dec_run);
        cat_params.addSetting(cb_convert);
        cat_params.initializeSettings();
        cat_params.generateClose();
        cat_params.generateTitle();
        pnl_params.setPosition(CAM_WIDTH / 16, pnl_params.getPosition().y);
        pnl_params.setVisible(false);
    
        cat_team.registerPanel(pnl_team);
        cat_team.addSetting(stat_rock);
        cat_team.addSetting(stat_paper);
        cat_team.addSetting(stat_scissors);
        cat_team.initializeSettings();
        cat_team.generateClose();
        cat_team.generateTitle();
        pnl_team.setPosition(CAM_WIDTH / 16, pnl_team.getPosition().y);
        pnl_team.setVisible(false);
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
        if(setting == cat_params){
            save = true;
            prefs[2] = value;
        }
        if(setting == cat_team){
            save = true;
            System.out.println(cat_team.getData());
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
        
        loadParameters(Wars.PARAMS.getParams());
    }
    
    @Override
    public void doAction(Button b, Type t) {
        if(b == back && t == Type.CLICKED){
            Wars.instance.setScreen(Wars.scr_title);
        }
    }
}
