package com.xemplarsoft.games.cross.rps.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Collections;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.xemplarsoft.games.cross.rps.CreditsScroller;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.utils.xwt.*;

import static com.xemplarsoft.games.cross.rps.CreditsScroller.*;

public final class CreditsScreen extends ScreenAdapter {
    public static final float CAM_WIDTH = 18F, CAM_HEIGHT_MIN = 22F, CAM_HEIGHT_MAX = 40F, PADDING = 0.25F;
    public static float CAM_HEIGHT;
    
    public static String[] CREDITS = new String[]{
    "t20000c6:Lead Developer",
    "n40000c6:Jaxon ( @xemplarsoft )",
    "n40000c6:",
    "t20000c6:Junior Developer",
    "n40000c6:Tobi (@tobi#0256)",
    "n40000c6:",
    "t20000c6:UI Assets",
    "n40000c6:Rye (@Rye#5033)",
    "n40000c6:4-everThe4C-er",
    "n40000c6:",
    "t20000c6:Game Assets",
    "n40000c6:Chill",
    "n40000c6:",
    "t20000c6:Sound FX",
    "n40000c6:Panini (Jaxon's Wife)",
    "n40000c6:",
    "t20000c6:Server Admins",
    "n40000c6:@BorderlineAxolotl#3067 ",
    "n40000c6:@LillianRose#5443 ",
    "n40000c6:",
    "t20000c6:Server Mods",
    "n40000c6:James (@DeFy#9182)",
    "n40000c6:spyro12767",
    "n40000c6:",
    "t20000c6:Discord Lurkers",
    "n40000c6:James (@DeFy#9182)",
    "n40000c6:spyro12767",
    "n40000c6:"
    };
    
    
    protected float SPEED = 2F;
    protected BitmapFont font;
    protected SegmentedButton title;
    
    protected boolean scrollDir = true, scrolling = true;
    private float yOff = 1F;
    
    protected SegmentedButton back;
    
    public CreditsScreen() {
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT_MAX);
        vp = new ExtendViewport(CAM_WIDTH, CAM_HEIGHT_MIN, CAM_WIDTH, CAM_HEIGHT_MAX, cam);
        USE_HUD_UNITS = false;
        this.font = Wars.fnt_text;
    }
    
    protected float usedY;
    public void renderSelf(float delta) {
        usedY = 0;
        
        if(scrolling) this.yOff += (delta * SPEED) * (scrollDir ? 1 : -1);
        for(String s : CREDITS){
            if(isTitle(s)) renderTitle(batch, s);
            else renderNormal(batch, s);
        }
        if(yOff - usedY > CAM_HEIGHT) yOff = 0F;
    }
    
    public void renderTitle(SpriteBatch batch, String line){
        BitmapFont font = this.font;
        this.font = Wars.fnt_title;
        renderNormal(batch, line);
        this.font = font;
    }
    
    public void renderNormal(SpriteBatch batch, String line){
        Color c = CreditsScroller.getColor(line);
        int cols = cols(line);
        int align = getAlignment(line);
        int size = getSize(line);
        String[] contents = getContents(line);
        
        float sX = font.getScaleX();
        float sY = font.getScaleY();
        
        font.getData().setScale(sX * ((float)size) / 3, sY * ((float)size) / 3);
        usedY += font.getLineHeight() + PADDING;
        
        Color prev = font.getColor();
        font.setColor(c);
        
        if(cols > 2){
            int alignL = align == Align.top ? Align.left : align;
            int alignC = align == Align.top ? Align.center : align;
            int alignR = align == Align.top ? Align.right : align;
            font.draw(batch, contents[0], 1, yOff + usedY, CAM_WIDTH / 3F, alignL, true);
            font.draw(batch, contents[1], CAM_WIDTH / 3F, yOff + usedY, CAM_WIDTH / 3F, alignC, true);
            font.draw(batch, contents[2], CAM_WIDTH / 3F * 2F, yOff + usedY, CAM_WIDTH / 3F, alignR, true);
            
            font.setColor(prev);
            font.getData().setScale(sX, sY);
            return;
        }
        if(cols > 1){
            int alignL = align == Align.top ? Align.left : align;
            int alignR = align == Align.top ? Align.right : align;
            font.draw(batch, contents[0], 1, yOff + usedY, CAM_WIDTH / 2F, alignL, true);
            font.draw(batch, contents[1], CAM_WIDTH / 2F, yOff + usedY, CAM_WIDTH / 2F, alignR, true);
            
            font.setColor(prev);
            font.getData().setScale(sX, sY);
            return;
        }
        align = align == Align.top ? Align.center : align;
        font.draw(batch, contents[0], 0, yOff - usedY, CAM_WIDTH, align, true);
        
        font.setColor(prev);
        font.getData().setScale(sX, sY);
    }
    
    public void resizeSelf(int width, int height) {
        CAM_HEIGHT = (float) height/width * CAM_WIDTH;
        vp.update(width, height, false);
        cam.position.set(CAM_WIDTH / 2F, CAM_HEIGHT / 2F, 0);
        cam.update();
        removeAllUI();
    
        title = new SegmentedButton(0, CAM_HEIGHT, CAM_WIDTH, CAM_WIDTH / 8, "Credits");
        title.setTextures("header_options");
        title.setFont(Wars.fnt_button);
        title.setAlignment(Align.center);
    
        back = new SegmentedButton(0, CAM_HEIGHT, CAM_WIDTH / 8, CAM_WIDTH / 8, "");
        back.setIcon(Wars.ur("back"));
        back.setAction(new Action() {
            public void doAction(Button b, Type t) {
                if(b == back && t.equals(Type.CLICKED)){
                    yOff = 0;
                    Wars.instance.setScreen(Wars.scr_title);
                }
            }
        });
        
        addToUI(title);
        addToUI(back);
    }
}