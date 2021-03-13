package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.xemplarsoft.games.cross.rps.Wars.FADE_DURATION;

public class FadeView extends ImageView{
    protected float pos, duration;
    protected boolean started, in;
    protected Action a;
    public FadeView(float width, float height, TextureRegion image, boolean in) {
        super(0, 0, width, height, image);
        duration = FADE_DURATION;
        this.in = in;
        setTransparency(this.in ? 0 : 1);
    }
    
    public boolean isRunning(){
        return started;
    }
    
    public void setAction(Action a){
        this.a = a;
    }
    
    public void start(){
        setTransparency(in ? 0 : 1);
        started = true;
    }
    
    public void update(float delta){
        if(!started) return;
        pos += delta;
        if(pos >= duration){
            pos = duration;
            setTransparency(in ? 1 : 0);
            if(a != null) a.doAction(null, null);
            started = false;
            return;
        }
        float amt = pos / duration;
        setTransparency(in ? amt : 1F - amt);
    }
    
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
}
