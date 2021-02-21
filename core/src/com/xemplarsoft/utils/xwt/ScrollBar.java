package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xemplarsoft.games.cross.rps.Wars;

public class ScrollBar extends Panel implements Action{
    protected TextureRegion bar;
    protected Button up, down;
    protected int max, pos;
    protected ScrollPanel panel;
    protected float barHeight, barX;

    public ScrollBar(float x, float y, float height, ComponentHandler handler){
        super(x, y, 0.5F, height, null, handler);
        barX = x + width - 0.59375F;

        up = new Button(barX, y + height, 0.5F, 0.5F, "");
        down = new Button(barX, y + 0.5F, 0.5F, 0.5F, "");
        barHeight = height - 1F;

        up.setNormalBG(Wars.ur("scroll_up"));
        down.setNormalBG(Wars.ur("scroll_down"));

        up.setPressedBG(Wars.ur("scroll_up_press"));
        down.setPressedBG(Wars.ur("scroll_down_press"));

        addView(up);
        addView(down);

        up.setAction(this);
        down.setAction(this);
        bar = Wars.ur("scroll_bar");
    }

    public void setMax(int max){
        this.max = max;
    }

    public void bindScrollPanel(ScrollPanel panel){
        this.panel = panel;
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
        up.setVisible(visible);
        down.setVisible(visible);
    }

    public void doAction(Button b, Type t) {
        if(b == up && t == Type.CLICKED){
            panel.scrollUp();
            if(pos > 0) pos--;
        } else if(b == down && t == Type.CLICKED){
            panel.scrollDown();
            if(pos < max) pos++;
        }
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
        if(visible) {
            float y = 0;

            if (pos == 0) {
                y = this.y + height - 1.09375F;
            } else if (pos == max) {
                y = this.y + 0.59375F;
            } else {
                y = this.y + (barHeight / max * (max - pos)) + 0.29375F;
            }

            batch.draw(bar, barX, y, 0.5F, 0.5F);
        }
    }
}
