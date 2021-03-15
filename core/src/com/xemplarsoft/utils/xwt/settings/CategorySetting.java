package com.xemplarsoft.utils.xwt.settings;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.utils.Base64;
import com.xemplarsoft.utils.xwt.Button;
import com.xemplarsoft.utils.xwt.ComponentHandler;
import com.xemplarsoft.utils.xwt.Panel;
import com.xemplarsoft.utils.xwt.SegmentedButton;

import static com.xemplarsoft.games.cross.rps.screens.OptionsScreen.CAM_WIDTH;

public class CategorySetting extends AbstractSetting<String> implements SettingChangeListener{
    protected Array<AbstractSetting<?>> settings;
    protected Panel content;
    protected Button trigger;
    public CategorySetting(String title, String desc, float width, float y, ComponentHandler handler) {
        super(title, desc, width, y, handler);
        trigger = new Button(x, y + height, width, height, "");
        trigger.setAction(this);
        handler.addToUI(trigger);
        
        settings = new Array<>();
    }
    
    public void registerPanel(Panel p){
        this.content = p;
        this.handler.addToUI(p);
    }
    
    public void addSetting(AbstractSetting<?> setting){
        setting.setListener(this);
        this.content.addView(setting);
        this.settings.add(setting);
    }
    
    public void doAction(Button b, Type t) {
        if(b == trigger && t == Type.CLICKED && !content.isVisible()){
            if(content != null) content.setVisible(true);
        }
        if(b.getActionCommand().equals("close") && t == Type.CLICKED){
            content.setVisible(false);
        }
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        trigger.setVisible(visible);
    }
    
    public String getData() {
        StringBuilder builder = new StringBuilder();
        for(AbstractSetting<?> s : settings){
            Object data = s.getData();
            if(data == null) data = 0;
            builder.append('#').append(Base64.encodeToString(data.toString()));
        }
        return builder.substring(1);
    }
    
    public void generateClose(){
        Vector2 pos = content.getPosition();
        Button close = new Button(pos.x + 2F, pos.y + 2F + CAM_WIDTH / 8F - content.getHeight(), CAM_WIDTH / 8F, CAM_WIDTH / 8F, "");
        close.setBG(Wars.ur("close"));
        close.setAction(this);
        close.setActionCommand("close");
        content.addView(close);
    }
    
    public void generateTitle(){
        Vector2 pos = content.getPosition();
        final float paramsPadding = content.getWidth() / 16F;
        
        SegmentedButton title = new SegmentedButton(paramsPadding, pos.y + 1.1F, content.getWidth() - paramsPadding * 2F, 2.2F, this.title);
        title.setOffY(-0.25F);
        title.setTextures("lbl_stone");
        title.setFont(Wars.fnt_button);
        title.setAlignment(Align.center);
        content.addView(title);
    }
    
    public void initializeSettings(){
        if(this.data == null || this.data.length() < 1) return;
        
        String[] data = this.data.split("#");
        int index = 0;
        for(AbstractSetting<?> s : settings){
            if(index >= data.length) s.setData("");
            else s.setData(Base64.decodeToString(data[index]));
            index++;
        }
    }
    
    public void changed(AbstractSetting<?> setting, String value) {
        this.data = getData();
        
        if(listener != null){
            listener.changed(setting, value);
            listener.changed(this, getData());
        }
    }
}
