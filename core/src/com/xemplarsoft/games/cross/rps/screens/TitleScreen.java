package com.xemplarsoft.games.cross.rps.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.model.BounceBG;
import com.xemplarsoft.utils.xwt.*;

public class TitleScreen extends ScreenAdapter {
    public static final float CAM_WIDTH = 18F, CAM_HEIGHT_MIN = 22F, CAM_HEIGHT_MAX = 40F;
    public static float CAM_HEIGHT;
    
    public ImageView view;
    public SegmentedButton play, options, credits, builder;
    public Label copy;
    
    public BounceBG bg;
    public TitleScreen() {
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT_MAX);
        vp = new ExtendViewport(CAM_WIDTH, CAM_HEIGHT_MIN, CAM_WIDTH, CAM_HEIGHT_MAX, cam);
    }
    
    public void renderSelf(float delta) {
        bg.update(delta);
        bg.render(batch);
    }
    
    public void resizeSelf(int width, int height) {
        CAM_HEIGHT = (float) height/width * CAM_WIDTH;
        vp.update(width, height, false);
        cam.position.set(CAM_WIDTH / 2, CAM_HEIGHT / 2F, 0);
        cam.update();
        removeAllUI();
        
        view = new ImageView(1F, CAM_HEIGHT - (CAM_WIDTH - 2F) - 1F, CAM_WIDTH - 2F, CAM_WIDTH - 2F, Wars.ur("start_title"));
        bg = new BounceBG(Wars.ur("bg"), CAM_HEIGHT * 2, CAM_HEIGHT * 2);
        
        play = new SegmentedButton(1F, CAM_HEIGHT - (CAM_WIDTH - 2F) + 2.5F, CAM_WIDTH - 2F, 2.5F, "Start");
        play.setTextures("btn_text");
        play.setAlignment(Align.center);
        play.setFont(Wars.fnt_button);
        play.setIcon(Wars.ur("play"));
        play.setAction(new Action() {
            public void doAction(Button b, Type t) {
                if(b == play && t.equals(Type.CLICKED)) Wars.instance.setScreen(Wars.scr_game);
            }
        });
    
        builder = new SegmentedButton(2F, CAM_HEIGHT - (CAM_WIDTH - 2F) - 0.2F, CAM_WIDTH - 4F, 1.8F, "Scenarios");
        builder.setTextures("btn_text");
        builder.setAlignment(Align.center);
        builder.setFont(Wars.fnt_sub);
        builder.setAction(new Action() {
            public void doAction(Button b, Type t) {
                if(b == options && t.equals(Type.CLICKED)) Wars.instance.setScreen(Wars.scr_builder);
            }
        });
        
        options = new SegmentedButton(2F, CAM_HEIGHT - (CAM_WIDTH - 2F) - 2.2F, CAM_WIDTH - 4F, 1.8F, "Options");
        options.setTextures("btn_text");
        options.setAlignment(Align.center);
        options.setFont(Wars.fnt_sub);
        options.setAction(new Action() {
            public void doAction(Button b, Type t) {
                if(b == options && t.equals(Type.CLICKED)) Wars.instance.setScreen(Wars.scr_options);
            }
        });
        
        credits = new SegmentedButton(2F, CAM_HEIGHT - (CAM_WIDTH - 2F) - 4.2F, CAM_WIDTH - 4F, 1.8F, "Credits");
        credits.setTextures("btn_text");
        credits.setAlignment(Align.center);
        credits.setFont(Wars.fnt_sub);
        credits.setAction(new Action() {
            public void doAction(Button b, Type t) {
                if(b == credits && t.equals(Type.CLICKED)) Wars.instance.setScreen(Wars.scr_credits);
            }
        });
    
        copy = new Label(2F, 1.8F, CAM_WIDTH - 4F, 1.8F, "Xemplar Softworks, LLC © 2018 - 2021");
        copy.setAlignment(Align.center);
        copy.setFont(Wars.fnt_text);
        
        addToUI(view);
        addToUI(play);
        addToUI(builder);
        addToUI(options);
        addToUI(credits);
        addToUI(copy);
        
        if(OptionsScreen.getBoolean(1)){
            Wars.mx_title.setPosition(0);
            Wars.mx_title.play();
        }
    }
}