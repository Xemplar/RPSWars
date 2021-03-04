package com.xemplarsoft.utils.xwt.settings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CheckboxSetting extends AbstractSetting<Boolean> {
    public CheckboxSetting(String title, String desc, float width, float y) {
        super(title, desc, width, y);
    }
    
    public void render(SpriteBatch batch) {
        super.render(batch);
        if(data){
            //Render Checked
        } else {
            //Render Unchecked
        }
    }
    
    public void clicked(){
        data = !data;
    }
}
