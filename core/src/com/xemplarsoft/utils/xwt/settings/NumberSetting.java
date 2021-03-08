package com.xemplarsoft.utils.xwt.settings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.xemplarsoft.utils.xwt.Button;
import com.xemplarsoft.utils.xwt.ComponentHandler;
import com.xemplarsoft.utils.xwt.Label;
import com.xemplarsoft.utils.xwt.SegmentedButton;

public class NumberSetting extends AbstractSetting<Integer>{
    protected SegmentedButton p, m;
    protected Label amt;
    
    protected int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;
    
    public NumberSetting(String title, String desc, float width, float y, ComponentHandler handler){
        super(title, desc, width, y, handler);
        p = new SegmentedButton(width / 4 * 3, y + height, width / 4, height / 3, "+");
        p.setTextures("btn_action");
        p.setAlignment(Align.center);
        m = new SegmentedButton(width / 4 * 3, y + height / 3, width / 4, height / 3, "-");
        m.setTextures("btn_action");
        m.setAlignment(Align.center);
        
        amt = new Label(width / 4 * 3, y + height / 3, width / 4, height / 3, "0");
        amt.setAlignment(Align.center);
        data = 0;
        
        p.setAction(this);
        m.setAction(this);
        
        addView(p);
        addView(amt);
        addView(m);
    }
    
    public void setData(Integer data) {
        super.setData(data);
        update();
    }
    
    public void setBounds(int min, int max){
        this.min = min;
        this.max = max;
    }
    
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
    
    public void doAction(Button b, Type t) {
        super.doAction(b, t);
        if(b == p && t == Type.CLICKED && data < max) {
            data++;
            update();
            if(listener != null) listener.changed(this, data.toString());
        }
        if(b == m && t == Type.CLICKED && data > min){
            data--;
            update();
            if(listener != null) listener.changed(this, data.toString());
        }
    }
    
    public void update(){
        this.amt.setText(data + "");
    }
}
