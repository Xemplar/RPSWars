package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xemplarsoft.games.cross.rps.Wars;

public class SegmentedButton extends Button{
    protected TextureRegion left, mid, right, icon;
    public SegmentedButton(float x, float y, float width, float height, String text){
        super(x, y, width, height, text);
    }
    
    public void setTextures(String name){
        left = Wars.ur(name, 0);
        mid = Wars.ur(name, 1);
        right = Wars.ur(name, 2);
        this.font = Wars.fnt_button;
    }
    
    public void setIcon(TextureRegion icon){
        this.icon = icon;
    }
    
    public void render(SpriteBatch batch){
        render(batch, this.font);
    }
    
    public void render(SpriteBatch batch, BitmapFont font){
        if(visible) {
            if (border != null) border.render(batch, this);
            
            float ratio = 0.19512195F;
            batch.draw(left, x, y - height, ratio * height, height);
            batch.draw(right, x + width - ratio * height, y - height, ratio * height, height);
            batch.draw(mid, x + ratio * height, y - height, width - (ratio * height * 2), height);
            if(icon != null) batch.draw(icon, x + 0.5F, y - height + 0.5F, height - 1F, height - 1F);
            
            font.draw(batch, text, x, y - height * 0.25F, width, alignment, true);
        }
    }
}
