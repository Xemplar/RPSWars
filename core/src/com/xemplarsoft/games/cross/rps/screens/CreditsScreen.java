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
    public static final float CAM_WIDTH = 18F, CAM_HEIGHT_MIN = 32F, CAM_HEIGHT_MAX = 40F, PADDING = 0.25F;
    public static float CAM_HEIGHT;
    public static String[] CREDITS = new String[]{
    "n40000c6:",
    "t20000c6:Lead Developer",
    "n40000c6:Jaxon ( @xemplarsoft )",
    "n40000c6:",
    "t20000c6:Junior Developer",
    "n40000c6:Tobi (@tobi#0256)",
    "n40000c6:",
    "t20000c6:UI Assets",
    "n40000c6:4-everThe4C-er",
    "n40000c6:Jaxon ( @xemplarsoft )",
    "n40000c6:Rye (@Rye#5033)",
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
    "n40000c6:@Robberino#5932",
    "n40000c6:@HappyHero#7370",
    "n40000c6:@Lord Zahnker#7893",
    "n40000c6:@unCrossedd#6969",
    "n40000c6:"
    };
    
    
    protected float SPEED = 2F;
    protected BitmapFont font;
    protected SegmentedButton title;
    
    protected boolean scrollDir = true, scrolling = true;
    private float yOff = 1F;
    
    protected Button back;
    public FadeView fadeIn, fadeOut;
    
    public CreditsScreen() {
        cam = new OrthographicCamera(CAM_WIDTH, CAM_HEIGHT_MAX);
        vp = new ExtendViewport(CAM_WIDTH, CAM_HEIGHT_MIN, CAM_WIDTH, CAM_HEIGHT_MAX, cam);
        USE_HUD_UNITS = false;
        this.font = Wars.fnt_text;
    }
    
    protected float usedY, prevY;
    public void renderSelf(float delta) {
        if(fadeIn != null) fadeIn.update(delta);
        if(fadeOut != null) fadeOut.update(delta);
    }
    
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        prevY = usedY;
        usedY = 0;
        
        if(prevY > 0F){
            float padding = 0.5F;
            prevY += padding * 2;
            int count = (int) Math.ceil(prevY / (CAM_HEIGHT - 2F));
            float width = CAM_WIDTH - padding * 2;
            float hC = width / 8;
            float hT = (prevY - (hC * 2F)) / count;
            boolean flip = true;
            batch.draw(Wars.ur("paper", 0), padding, yOff - hC, width, hC);
            for(int i = 0; i < count; i++){
                flip = !flip;
                batch.draw(Wars.ur("paper", 1), padding, yOff - hC - hT * (i + 1), width / 2F, hT / 2F, width, hT, 1, 1, flip ? 180 : 0);
            }
            batch.draw(Wars.ur("paper", 2), padding, yOff - hC * 2 - hT * (count), width, hC);
        }
        
        if(scrolling) this.yOff += (delta * SPEED * (touches * 3 + 1)) * (scrollDir ? 1 : -1);
        for(String s : CREDITS){
            if(isTitle(s)) renderTitle(batch, s);
            else renderNormal(batch, s);
        }
        if(yOff - usedY > CAM_HEIGHT) yOff = 0F;
        title.render(batch);
        back.render(batch);
        batch.end();
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
        
        title = new SegmentedButton(2F, CAM_HEIGHT, CAM_WIDTH - 4F, CAM_WIDTH / 8, "Credits");
        title.setTextures("lbl_stone");
        title.setFont(Wars.fnt_button);
        title.setAlignment(Align.center);
        title.setOffY(-0.125F);
    
        back = new Button(CAM_WIDTH / 16 * 2, CAM_WIDTH / 16 * 4, CAM_WIDTH / 8, CAM_WIDTH / 8, "");
        back.setBG(Wars.ur("close"));
        back.setAction(new Action() {
            public void doAction(Button b, Type t) {
                if(b == back && t.equals(Type.CLICKED)){
                    yOff = 0;
                    fadeIn.start();
                }
            }
        });
        
        addToUI(title);
        addToUI(back);
        addToUI(fadeIn);
        addToUI(fadeOut);
    }
    
    private int touches = 0;
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touches++;
        return super.touchDown(screenX, screenY, pointer, button) || true;
    }
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touches--;
        return super.touchUp(screenX, screenY, pointer, button) || true;
    }
}