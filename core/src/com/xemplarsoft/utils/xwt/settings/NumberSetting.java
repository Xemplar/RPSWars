package com.xemplarsoft.utils.xwt.settings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.utils.xwt.Button;
import com.xemplarsoft.utils.xwt.ComponentHandler;
import com.xemplarsoft.utils.xwt.Label;
import com.xemplarsoft.utils.xwt.SegmentedButton;

public class NumberSetting extends AbstractSetting<Integer>{
    protected Button p, m;
    protected Label amt;
    
    protected int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;
    
    public NumberSetting(String title, String desc, float width, float y, ComponentHandler handler){
        super(title, desc, width, y, handler);
        p = new Button(width / 16 * 12, y + height, width / 8, height / 3, "+");
        p.setBG(Wars.ur("btn_icon", 0), Wars.ur("btn_icon", 1));
        p.setAlignment(Align.center);
        m = new Button(width / 16 * 12, y + height / 3, width / 8, height / 3, "-");
        m.setBG(Wars.ur("btn_icon", 0), Wars.ur("btn_icon", 1));
        m.setAlignment(Align.center);
        
        amt = new Label(width / 16 * 12, y + height / 3, width / 8, height / 3, "0");
        amt.setFont(Wars.fnt_desc);
        amt.setAlignment(Align.center);
        data = 0;
        
        p.setAction(this);
        m.setAction(this);
        
        addView(p);
        addView(amt);
        addView(m);
    }
    
    public void setData(String data) {
        try {
            this.data = Math.min(Math.max(Integer.parseInt(data), min), max);
        } catch (NumberFormatException e){
            this.data = min;
        }
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
