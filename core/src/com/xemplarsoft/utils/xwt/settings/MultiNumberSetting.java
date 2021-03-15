package com.xemplarsoft.utils.xwt.settings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.utils.xwt.*;

public class MultiNumberSetting extends AbstractSetting<String>{
    protected Array<TextureRegion> icons;
    protected Array<ImageView> icon_views;
    protected Array<NamedValue> values;
    protected Array<Button> pp;
    protected Array<Button> mm;
    protected Array<Label> amt;
    
    protected int spinnerCount;
    protected Panel container;
    public MultiNumberSetting(String title, float width, float y, Panel container) {
        super(title, "", width, y, container.getHandler());
        this.container = container;
        height = width / 4F + 2F;
    }
    
    public void setValues(NamedValue... values){
        this.values = new Array<>();
        this.values.addAll(values);
        this.spinnerCount = values.length;
    }
    
    public void setIcons(String name){
        icons = new Array<>();
        for(int i = 0; i < spinnerCount; i++){
            icons.add(Wars.ur(name, i));
        }
    }
    
    public void initializeSpinners(){
        if(pp == null) pp = new Array<>();
        if(mm == null) mm = new Array<>();
        if(amt == null) amt = new Array<>();
        if(icons != null) icon_views = new Array<>();
        pp.clear();
        mm.clear();
        amt.clear();
        
        spinnerCount = values.size;
        Vector2 pos = getPosition();
        
        final float padding = getWidth() / 16F;
        final float spinnerHeight = getHeight() - (fnt_title.getLineHeight() + padding);
        final float buttonWidth = (getWidth() - ((3 + spinnerCount) * padding)) / spinnerCount;
        final float buttonHeight = spinnerHeight / 3F;
        
        for(int i = 0; i < spinnerCount; i++){
            if(icons != null) {
                ImageView w = new ImageView(pos.x + ((buttonWidth * i) + (2 + i) * padding) + ((buttonWidth - buttonHeight) / 2F), pos.y + height + padding * 2.5F, buttonHeight, buttonHeight, icons.get(i));
                Label name = new Label(pos.x + (buttonWidth * i) + (2 + i) * padding, pos.y + height + padding * 1F, buttonWidth, buttonHeight, values.get(i).name);
                name.setFont(Wars.fnt_desc);
                name.setAlignment(Align.center);
                icon_views.add(w);
                container.addView(w);
                container.addView(name);
            }
            
            Button p = new Button(pos.x + (buttonWidth * i) + (2 + i) * padding, pos.y + (getHeight() - spinnerHeight) + buttonHeight * 2, buttonWidth, buttonHeight, "+");
            p.setBG(Wars.ur("btn_icon", 0), Wars.ur("btn_icon", 1));
            p.setAlignment(Align.center);
            p.setActionCommand(i + ":p");
            p.setAction(this);
            container.addView(p);
    
            Button m = new Button(pos.x + (buttonWidth * i) + (2 + i) * padding, pos.y + (getHeight() - spinnerHeight) + 0.4F, buttonWidth, buttonHeight, "-");
            m.setBG(Wars.ur("btn_icon", 0), Wars.ur("btn_icon", 1));
            m.setAlignment(Align.center);
            m.setActionCommand(i + ":m");
            m.setAction(this);
            container.addView(m);
    
            Label a = new Label(pos.x + (buttonWidth * i) + (2 + i) * padding, pos.y + (getHeight() - spinnerHeight), buttonWidth, buttonHeight, "0");
            a.setFont(Wars.fnt_desc);
            a.setAlignment(Align.center);
            container.addView(a);
            
            pp.add(p);
            mm.add(m);
            amt.add(a);
            reloadAmt(i);
        }
    }
    
    public void render(SpriteBatch batch) {
        if(icons != null){
        
        }
        super.render(batch);
    }
    
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        for(int i = 0; i < spinnerCount; i++){
            pp.get(i).setVisible(visible);
            mm.get(i).setVisible(visible);
            amt.get(i).setVisible(visible);
            reloadAmt(i);
        }
    }
    
    public void doAction(Button b, Type t) {
        if(t == Type.CLICKED){
            String command = b.getActionCommand();
            if(command.equals("")) return;
            
            String[] args = command.split(":");
            int id = Integer.parseInt(args[0]);
            NamedValue v = values.get(id);
            if(args[1].equals("p")){
                v.increment();
                reloadAmt(id);
                if(listener != null) listener.changed(this, getData());
            }
            if(args[1].equals("m")){
                v.decrement();
                reloadAmt(id);
                if(listener != null) listener.changed(this, getData());
            }
        }
    }
    
    private void reloadAmt(int index){
        amt.get(index).setText(values.get(index).getValue());
    }
    
    public void setData(String data) {
        this.data = data;
        String[] dat = data.split("#");
        int i = 0;
        for(NamedValue v : values){
            if(i < dat.length) v.value = dat[i++];
            else v.value = 1 + "";
        }
    }
    
    public String getData() {
        StringBuilder b = new StringBuilder();
    
        for(NamedValue v : values){
            b.append("#").append(v.value);
        }
    
        return b.substring(1);
    }
    
    public static class NamedValue{
        private static int ID_COUNTER = 0;
        public final boolean integer;
        public final int ID;
        public final String name;
        private String value;
        public int min, max, step;
        
        private NamedValue(String name, String value){
            this(name, value, false);
        }
    
        private NamedValue(String name, String value, boolean integer){
            this.ID = ID_COUNTER; ID_COUNTER++;
            this.name = name;
            this.value = value;
            this.integer = integer;
            
            if(!integer){
                this.min = Float.floatToIntBits(0F);
                this.max = Float.floatToIntBits(10F);
                this.value = 1F + "";
                this.step = Float.floatToIntBits(1F);
            } else {
                this.min = 0;
                this.max = 10;
                this.value = 1 + "";
            }
        }
    
        public void increment(){
            int ret = 0;
            try {
                ret = Integer.parseInt(value) + (integer ? step : 1);
            } catch (NumberFormatException e){
                try {
                    ret = (int)(Float.parseFloat(value) / step) + (integer ? step : 1);
                } catch (NumberFormatException ex){
                    ret = 1;
                }
            }
            this.value = ret + "";
        }
    
        public void decrement(){
            int ret = 0;
            try {
                ret = Integer.parseInt(value) - (integer ? step : 1);
            } catch (NumberFormatException e){
                try {
                    ret = (int)(Float.parseFloat(value) / step) - (integer ? step : 1);
                } catch (NumberFormatException ex){
                    ret = 1;
                }
            }
            this.value = ret + "";
        }
        
        public void setBounds(int max, int min, int step){
            this.max = max;
            this.min = min;
            this.step = step;
        }
    
        public void setBounds(float max, float min, float step){
            this.max = Float.floatToIntBits(max);
            this.min = Float.floatToIntBits(min);
            this.step = Float.floatToIntBits(step);
        }
        
        public String getValue(){
            if(integer) return value;
            else{
                float ret = 0;
                try {
                    ret = Integer.parseInt(value) * Float.intBitsToFloat(step);
                } catch (NumberFormatException e){
                    try {
                        ret = Float.parseFloat(value);
                    } catch (NumberFormatException ex){
                        ret = 0;
                    }
                }
                return ret + "";
            }
        }
        
        public void setValue(int i){
            this.value = i + "";
        }
        
        public void setValue(float f){
            this.value = (int)(f / step) + "";
        }
    
        public static NamedValue ni(String name, String value){
            return new NamedValue(name, value);
        }
    
        public static NamedValue ni(String name, String value, boolean integer){
            return new NamedValue(name, value, integer);
        }
    }
}
