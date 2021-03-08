package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xemplarsoft.games.cross.rps.Wars;

public class SegmentedButton extends Button{
    protected TextureRegion left, mid, right, icon;
    protected float offY = 0F, offX = 0F;
    
    public SegmentedButton(float x, float y, float width, float height, String text){
        super(x, y, width, height, text);
        this.font = Wars.fnt_button;
    }
    
    public void setTextures(String name){
        left = Wars.ur(name, 0);
        mid = Wars.ur(name, 1);
        right = Wars.ur(name, 2);
    }
    
    public void setOffY(float y){
        this.offY = y;
    }
    
    public void setOffX(float x){
        this.offX = x;
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
            if(left != null) batch.draw(left, x, y - height, ratio * height, height);
            if(right != null) batch.draw(right, x + width - ratio * height, y - height, ratio * height, height);
            if(mid != null) batch.draw(mid, x + ratio * height, y - height, width - (ratio * height * 2), height);
            if(icon != null) batch.draw(icon, x + 0.5F, y - height + 0.5F, height - 1F, height - 1F);
            
            font.draw(batch, text, x + offX, y - ((height - font.getCapHeight()) / 2) + offY, width, alignment, true);
        }
    }
}
