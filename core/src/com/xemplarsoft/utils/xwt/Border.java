package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Border {
    protected NinePatch border;

    public Border(TextureRegion region){
        this.border = new NinePatch(region, 4, 4, 4, 4);
        this.border.setTopHeight(0.0625F);
        this.border.setBottomHeight(0.0625F);
        this.border.setLeftWidth(0.0625F);
        this.border.setRightWidth(0.0625F);
    }

    public void raster(float width, float height){
        this.border.setMiddleWidth(width - (0.0625F * 2));
        this.border.setMiddleHeight(height - (0.0625F * 2));
    }

    public void render(SpriteBatch batch, AbstractComponent component){
        if(component.visible) {
            border.draw(batch, component.x, component.y, component.width, component.height);
        }
    }
}
