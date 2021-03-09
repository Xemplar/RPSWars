package com.xemplarsoft.utils.xwt.transition;

public abstract class AbstractInterpolator {
    protected boolean run = false;
    protected float duration, pos;
    protected boolean invert;
    
    public AbstractInterpolator(float duration, boolean invert){
        this.duration = duration;
        this.invert = invert;
    }
    
    public void update(float delta){
        if(!run) return;
        
        pos += delta;
        if(pos >= duration) {
            pos = duration;
            run = false;
        }
    }
    public abstract float getMultiplier();
    
    public void start(){
        run = true;
        pos = 0;
    }
    
    public boolean finished(){
        return !run;
    }
}
