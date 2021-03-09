package com.xemplarsoft.utils.xwt.transition;

import com.xemplarsoft.utils.xwt.AbstractComponent;

public abstract class AbstractTransition {
    protected AbstractComponent comp;
    protected AbstractInterpolator interpolator;
    protected boolean run = false;
    protected float duration;
    protected AbstractTransition(float duration){
        this.duration = duration;
        this.interpolator = new LinearInterpolator(duration, false);
    }
    
    public void registerComponent(AbstractComponent comp){
        this.comp = comp;
    }
    
    public boolean finished(){
        return !run;
    }
    
    public void start() {
        run = true;
        interpolator.start();
    }
    
    public void setInterpolator(AbstractInterpolator interpolator){
        this.interpolator = interpolator;
    }
    
    public void update(float delta){
        if(run) interpolator.update(delta);
    }
}
