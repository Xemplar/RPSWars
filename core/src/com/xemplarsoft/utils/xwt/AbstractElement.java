package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractElement {
    protected boolean visible = true;
    protected String tag;
    protected float x, y, width, height;

    public Vector2 getPosition(){
        return new Vector2(x, y);
    }
    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void setPosition(Vector2 v){
        this.x = v.x;
        this.y = v.y;
    }
    
    public float getWidth(){
        return width;
    }
    
    public float getHeight(){
        return height;
    }
    
    public void setVisible(boolean visible){
        this.visible = visible;
    }
    public boolean isVisible(){
        return visible;
    }

    public abstract void render(SpriteBatch batch);
}
