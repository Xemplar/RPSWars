package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xemplarsoft.games.cross.rps.Wars;

public class Checkbox extends Button implements Action{
    protected TextureRegion ch, un;
    protected boolean checked = false;
    protected Action pass;
    public Checkbox(float x, float y, float width, float height){
        super(x, y, width, height, "");
        this.action = this;
    }
    
    public void setTextures(String name){
        this.ch = Wars.ur(name, 0);
        this.un = Wars.ur(name, 1);
    }
    
    public void doAction(Button b, Type t) {
        if(b == this && t == Type.CLICKED) checked = !checked;
        this.pass.doAction(b, t);
    }
    
    public void setAction(Action a) {
        this.pass = a;
    }
    
    public boolean isChecked() {
        return checked;
    }
    
    public void setChecked(boolean checked){
        this.checked = checked;
    }
    
    public void render(SpriteBatch batch) {
        if(visible) {
            if (border != null) border.render(batch, this);
            batch.draw(checked ? ch : un, x, y - height, width, height);
        }
    }
}
