package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class ScrollPanel extends Panel {
    protected Array<Panel> panels = new Array<Panel>();

    protected int start, max;
    protected float panelHeight = 1F;
    protected ScrollBar bar;

    public ScrollPanel(float x, float y, float width, float height, TextureRegion background, ComponentHandler handler){
        super(x, y, width, height, background, handler);
        bar = new ScrollBar(x + width - 0.5F, y, height, handler);
        addView(bar);
        bar.bindScrollPanel(this);
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
        for(Panel p : panels) p.setVisible(visible);
        if(visible) raster();
    }

    public void addPanel(Panel p){
        panels.add(p);
        raster();
    }

    public void removePanel(Panel p){
        panels.removeValue(p, false);
        raster();
    }

    public void scrollUp(){
        if(start == 0) return;
        start--;
        raster();
    }

    public void scrollDown(){
        if(start < panels.size - max)
        start++;
        raster();
    }

    private void raster(){
        for(Panel p : panels) p.setVisible(false);

        max = (int)(this.height / panelHeight);
        bar.setMax(panels.size - max);

        for(int i = start; i < start + max; i++){
            if(panels.size <= i) break;
            Panel current = panels.get(i);
            current.setVisible(true);

            int index = i - start + 1;
            float y = this.y + this.height - (index * panelHeight);
            current.setPosition(this.x, y);
        }
    }
}
