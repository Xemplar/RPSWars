package com.xemplarsoft.utils.xwt.settings;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.utils.xwt.AbstractComponent;
import com.xemplarsoft.utils.xwt.Action;
import com.xemplarsoft.utils.xwt.Button;

public abstract class AbstractSetting<T> extends Button implements Action {
    protected String title, desc;
    protected BitmapFont font;
    protected T data;
    
    public void setData(T data){
        this.data = data;
    }
    
    public T getData(){
        return data;
    }
    
    public AbstractSetting(String title, String desc, float width, float y){
        super(0, y, width, width / 4F, "");
        this.title = title;
        this.desc = desc;
        this.width = width;
        this.height = width / 4F;
        this.y = y;
        this.font = Wars.fnt_text;
        this.setAction(this);
    }
    
    public void render(SpriteBatch batch) {
        if(border != null) border.render(batch, this);
        font.draw(batch, title, x + 0.5F, y - height + 1.25F, width * 0.75F, Align.left, false);
        float sx = font.getScaleX(), sy = font.getScaleY();
        font.getData().setScale(sx * 0.75F, sy * 0.75F);
        font.draw(batch, desc, x + 0.5F, y - height + 0.25F, width * 0.75F, Align.left, false);
        font.getData().setScale(sx, sy);
    }
    
    public void doAction(Button b, Type t) {
        if(b == this && t == Type.CLICKED){
            clicked();
        }
    }
    
    public abstract void clicked();
}
