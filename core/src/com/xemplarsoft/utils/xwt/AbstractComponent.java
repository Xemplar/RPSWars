package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * Created by Rohan on 5/13/2018.
 */
public abstract class AbstractComponent extends AbstractElement {
    protected boolean androidOnly;
    protected Border border;

    public boolean isVisible(){
        boolean android = Gdx.app.getType() == Application.ApplicationType.Android;
        if(!android && androidOnly) return false;
        return super.isVisible();
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
