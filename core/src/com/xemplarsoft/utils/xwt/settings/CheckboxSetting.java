package com.xemplarsoft.utils.xwt.settings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xemplarsoft.utils.xwt.Button;
import com.xemplarsoft.utils.xwt.Checkbox;
import com.xemplarsoft.utils.xwt.ComponentHandler;

public class CheckboxSetting extends AbstractSetting<Boolean> {
    protected Checkbox checkbox;
    public CheckboxSetting(String title, String desc, float width, float y, ComponentHandler handler) {
        super(title, desc, width, y, handler);
        checkbox = new Checkbox(width / 16 * 12, y + height, width / 8, width / 8);
        checkbox.setAction(this);
        checkbox.setTextures("checkbox");
        addView(checkbox);
    }
    
    @Override
    public void doAction(Button b, Type t) {
        super.doAction(b, t);
        if(b == checkbox && t == Type.CLICKED) {
            this.data = checkbox.isChecked();
            if(listener != null) listener.changed(this, data.toString());
        }
    }
    
    @Override
    public void setData(String data) {
        this.data = Boolean.parseBoolean(data);
        checkbox.setChecked(this.data);
    }
}
