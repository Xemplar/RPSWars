package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.xemplarsoft.utils.xwt.transition.AbstractInterpolator;
import com.xemplarsoft.utils.xwt.transition.AbstractTransition;

/**
 * Created by Rohan on 5/13/2018.
 */
public abstract class AbstractComponent extends AbstractElement {
    protected boolean androidOnly;
    protected Border border;
    protected AbstractTransition transition;

    public boolean isVisible(){
        boolean android = Gdx.app.getType() == Application.ApplicationType.Android;
        if(!android && androidOnly) return false;
        return super.isVisible();
    }

    public void updateTransition(float delta){
        if(transition != null)transition.update(delta);
    }
    
    public boolean transitionFinished(){
        return transition.finished();
    }
    
    public void startTransition(){
        transition.start();
    }
    
    public void setTransition(AbstractTransition t){
        this.transition = t;
        this.transition.registerComponent(this);
    }
    
    public void setInterpolator(AbstractInterpolator i){
        this.transition.setInterpolator(i);
    }
    
    public void setBorder(Border b){
        this.border = b;
    }

    public void setAndroidOnly(boolean b){
        androidOnly = b;
    }

    public boolean isAndroidOnly(){
        return androidOnly;
    }
}
