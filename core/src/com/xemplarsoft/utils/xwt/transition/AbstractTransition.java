package com.xemplarsoft.utils.xwt.transition;

import com.badlogic.gdx.utils.Array;
import com.xemplarsoft.utils.xwt.AbstractComponent;

public abstract class AbstractTransition {
    protected Array<TransitionListener> listeners;
    protected AbstractComponent comp;
    protected AbstractInterpolator interpolator;
    protected boolean run = false, fired = true;
    protected float duration;
    
    protected AbstractTransition(float duration){
        this.duration = duration;
        this.listeners = new Array<>();
        this.interpolator = new LinearInterpolator(false);
        this.interpolator.registerTransition(this);
    }
    
    public void addListener(TransitionListener l){
        this.listeners.add(l);
    }
    
    public void registerComponent(AbstractComponent comp){
        this.comp = comp;
    }
    
    public boolean finished(){
        return !run;
    }
    
    public void start() {
        notifyStarted();
        interpolator.start();
        run = true;
        fired = false;
    }
    
    public void setInterpolator(AbstractInterpolator interpolator){
        this.interpolator = interpolator;
        this.interpolator.registerTransition(this);
    }
    
    private final void notifyStarted(){
        for(TransitionListener l : listeners) l.startedT();
    }
    
    private final void notifyProgress(){
        for(TransitionListener l : listeners) l.updatedT(interpolator.pos, duration);
    }
    
    private final void notifyFinished(){
        for(TransitionListener l : listeners){
            l.finishedT();
        }
    }
    
    public void update(float delta){
        if(!run && !fired){
            fired = true;
            notifyFinished();
        }
        if(run){
            interpolator.update(delta);
            notifyProgress();
            System.out.println(interpolator.getMultiplier());
        }
    }
}
