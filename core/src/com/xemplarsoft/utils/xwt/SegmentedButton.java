package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xemplarsoft.games.cross.rps.Wars;

public class SegmentedButton extends Button{
    protected TextureRegion left, mid, right, icon;
    protected TextureRegion leftP, midP, rightP;
    protected float offY = 0F, offX = 0F;
    
    public SegmentedButton(float x, float y, float width, float height, String text){
        super(x, y, width, height, text);
        this.font = Wars.fnt_button;
    }
    
    public void setTextures(String name){
        left = Wars.ur(name, 0);
        mid = Wars.ur(name, 1);
        right = Wars.ur(name, 2);
        
        if(Wars.ur(name, 3) != null){
            leftP = Wars.ur(name, 3);
            midP = Wars.ur(name, 4);
            rightP = Wars.ur(name, 5);
        }
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
            
            float og = offY;
            float ratio = 1F;
            if(leftP == null || !held) {
                if(left != null) {
                    batch.draw(left, x, y - height, ratio * height, height);
                    batch.draw(right, x + width - ratio * height, y - height, ratio * height, height);
                    batch.draw(mid, x + ratio * height, y - height, width - (ratio * height * 2), height);
                }
                if(icon != null) batch.draw(icon, x + 0.625F, y - height + 0.875F, height - 1.25F, height - 1.25F);
                offY += 0.1F * (height / 2.2F);
            } else {
                batch.draw(leftP, x, y - height, ratio * height, height);
                batch.draw(rightP, x + width - ratio * height, y - height, ratio * height, height);
                batch.draw(midP, x + ratio * height, y - height, width - (ratio * height * 2), height);
                if(icon != null) batch.draw(icon, x + 0.625F, y - height + 0.35F, height - 1.25F, height - 1.25F);
                offY += -0.25F * (height / 2.2F);
            }
            
            font.draw(batch, text, x + offX, y - ((height - font.getCapHeight()) / 2) + offY, width, alignment, true);
            offY = og;
        }
    }
}
