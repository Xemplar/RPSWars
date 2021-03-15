package com.xemplarsoft.games.cross.rps.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.model.BounceBG;
import com.xemplarsoft.utils.xwt.*;

import static com.xemplarsoft.games.cross.rps.Wars.scr_game;
import static com.xemplarsoft.games.cross.rps.Wars.scr_menu;

public class TitleScreen extends ScreenAdapter implements Action{
    public static final float CAM_WIDTH = 18F, CAM_HEIGHT_MIN = 32F, CAM_HEIGHT_MAX = 40F;
    public static float CAM_HEIGHT;
    
    public Panel main;
    public ImageView view;
    public FadeView fadeIn, fadeOut;
    public SegmentedButton play, options, credits, builder;
    public Label copy;
    
    public BounceBG bg;
    public TitleScreen() {
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT_MAX);
        vp = new ExtendViewport(CAM_WIDTH, CAM_HEIGHT_MIN, CAM_WIDTH, CAM_HEIGHT_MAX, cam);
    }
    
    public void renderSelf(float delta) {
        bg.update(delta);
        if(fadeIn != null) fadeIn.update(delta);
        if(fadeOut != null) fadeOut.update(delta);
    }
    
    public void resizeSelf(int width, int height) {
        CAM_HEIGHT = (float) height/width * CAM_WIDTH;
        vp.update(width, height, false);
        cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2F, 0);
        cam.update();
        removeAllUI();
        
        setupUI();
        setupActions();
        
        if(!fadeOut.isRunning()) fadeOut.start();
        
        if(OptionsScreen.getBoolean(1) && !Wars.mx_title.isPlaying()){
            Wars.mx_title.setPosition(0);
            Wars.mx_title.play();
        }
    }
    
    private Button sender;
    private void setupActions(){
        play.setAction(this);
        builder.setAction(this);
        credits.setAction(this);
        options.setAction(this);
        
        fadeIn.setAction(new Action() {
            public void doAction(Button b, Type t) {
                if(sender == play) Wars.instance.setScreen(scr_game);
                if(sender == options) Wars.instance.setScreen(Wars.scr_builder);
                if(sender == options) Wars.instance.setScreen(Wars.scr_options);
                if(sender == credits) Wars.instance.setScreen(Wars.scr_credits);
            }
        });
    }
    
    public void doAction(Button b, Type t) {
        if(t == Type.CLICKED){
            sender = b;
            fadeIn.start();
        }
    }
    
    private void setupUI(){
        fadeIn = new FadeView(CAM_WIDTH, CAM_HEIGHT, Wars.ur("fade_black"), true);
        fadeOut = new FadeView(CAM_WIDTH, CAM_HEIGHT, Wars.ur("fade_black"), false);
        
        main = new Panel(0, 0, CAM_WIDTH, CAM_HEIGHT, null, this);
        view = new ImageView(1F, CAM_HEIGHT - (CAM_WIDTH - 2F) - 1F, CAM_WIDTH - 2F, CAM_WIDTH - 2F, Wars.ur("start_title"));
        bg = new BounceBG(Wars.ur("bg"), CAM_HEIGHT * 2, CAM_HEIGHT * 2);
    
        final float btn_height = 2.2F;
    
        play = new SegmentedButton(1F, CAM_HEIGHT - (CAM_WIDTH - 2F) + 2.5F, CAM_WIDTH - 2F, btn_height * 1.25F, "Start");
        play.setTextures("btn_stone");
        play.setAlignment(Align.center);
        play.setFont(Wars.fnt_button);
        play.setIcon(Wars.ur("play"));
    
        builder = new SegmentedButton(2F, play.getPosition().y - btn_height - 1F, CAM_WIDTH - 4F, btn_height, "Scenarios");
        builder.setTextures("btn_stone");
        builder.setAlignment(Align.center);
        builder.setFont(Wars.fnt_sub);
    
        options = new SegmentedButton(2F, builder.getPosition().y - btn_height - 0.2F, CAM_WIDTH - 4F, btn_height, "Options");
        options.setTextures("btn_stone");
        options.setAlignment(Align.center);
        options.setFont(Wars.fnt_sub);
    
        credits = new SegmentedButton(2F, options.getPosition().y - btn_height - 0.2F, CAM_WIDTH - 4F, btn_height, "Credits");
        credits.setTextures("btn_stone");
        credits.setAlignment(Align.center);
        credits.setFont(Wars.fnt_sub);
    
        copy = new Label(2F, 1.8F, CAM_WIDTH - 4F, 1.8F, "Xemplar Softworks, LLC © 2018 - 2021");
        copy.setAlignment(Align.center);
        copy.setFont(Wars.fnt_text);
    
        addToUI(bg);
        addToUI(view);
        main.addView(play);
        main.addView(builder);
        main.addView(options);
        main.addView(credits);
        addToUI(copy);
        addToUI(fadeIn);
        addToUI(fadeOut);
    }
}