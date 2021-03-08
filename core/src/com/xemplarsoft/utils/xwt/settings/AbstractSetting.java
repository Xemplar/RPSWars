package com.xemplarsoft.utils.xwt.settings;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.utils.xwt.Action;
import com.xemplarsoft.utils.xwt.Button;
import com.xemplarsoft.utils.xwt.ComponentHandler;
import com.xemplarsoft.utils.xwt.Panel;

public abstract class AbstractSetting<T> extends Panel implements Action {
    protected String title, desc;
    protected BitmapFont fnt_title, fnt_desc;
    protected T data;
    protected SettingChangeListener listener;
    
    public void setData(T data){
        this.data = data;
    }
    
    public T getData(){
        return data;
    }
    
    public AbstractSetting(String title, String desc, float width, float y, ComponentHandler handler){
        super(0, y, width, width / 4F, null, handler);
        this.title = title;
        this.desc = desc;
        this.width = width;
        this.height = width / 4F;
        this.y = y;
        this.fnt_desc = Wars.fnt_text;
        this.fnt_title = Wars.fnt_button;
    }
    
    public void setListener(SettingChangeListener l){
        this.listener = l;
    }
    
    public void render(SpriteBatch batch) {
        float sxd = fnt_desc.getScaleX(), syd = fnt_desc.getScaleY();
        float sxt = fnt_title.getScaleX(), syt = fnt_title.getScaleY();
        if(border != null) border.render(batch, this);
        fnt_title.getData().setScale(sxt * 0.825F, syt * 0.825F);
        fnt_title.draw(batch, title, x + width / 16, y + height, width * 0.75F, Align.left, false);
        fnt_desc.getData().setScale(sxd * 1.25F, syd * 1.25F);
        fnt_desc.draw(batch, desc, x + width / 16, y + height - fnt_title.getLineHeight(), width * 0.75F, Align.left, true);
        fnt_title.getData().setScale(sxt, syt);
        fnt_desc.getData().setScale(sxd, syd);
    }
    
    public void doAction(Button b, Type t) { }
}
