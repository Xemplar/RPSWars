package com.xemplarsoft.utils.xwt.settings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.utils.xwt.Button;
import com.xemplarsoft.utils.xwt.ComponentHandler;
import com.xemplarsoft.utils.xwt.Label;

public class DecimalSetting extends AbstractSetting<Float>{
    protected Button p, m;
    protected Label amt;
    
    protected float minF = Float.MIN_VALUE, maxF = Float.MAX_VALUE, increment = 1F;
    protected int min = (int)(minF / increment);
    protected int max = (int)(maxF / increment);
    protected int step = 0;
    
    public DecimalSetting(String title, String desc, float width, float y, ComponentHandler handler){
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
        data = 0F;
        
        p.setAction(this);
        m.setAction(this);
        
        addView(p);
        addView(amt);
        addView(m);
    }
    
    public void setData(String data) {
        try {
            this.data = Float.parseFloat(data);
        } catch (NumberFormatException e){
            this.data = Math.min(Math.max(minF, 1F), maxF);
        }
        step = (int)(this.data / increment);
        update();
    }
    
    public void setBounds(float min, float max, float increment){
        this.minF = min;
        this.maxF = max;
        this.increment = increment;
        this.min = (int)(this.minF / increment);
        this.max = (int)(this.maxF / increment);
    }
    
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
    
    public void doAction(Button b, Type t) {
        super.doAction(b, t);
        boolean update = false;
        if(b == p && t == Type.CLICKED && step < max) {
            step++;
            update = true;
        }
        if(b == m && t == Type.CLICKED && step > min){
            step--;
            update = true;
        }
        if(update){
            data = step * increment;
            update();
            if(listener != null) listener.changed(this, data.toString());
        }
    }
    
    public void update(){
        this.amt.setText(data + "");
    }
}
