package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageView extends AbstractComponent {
    protected TextureRegion image;
    protected float transparency = 1.0F;
    
    public ImageView(float x, float y, float width, float height, TextureRegion image){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public void setTransparency(float t){
        this.transparency = t;
    }
    
    public float getTransparency(){
        return this.transparency;
    }
    
    public void render(SpriteBatch batch) {
        if(visible) {
            if (border != null) border.render(batch, this);
            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, transparency);
            batch.draw(image, x, y, width, height);
            batch.setColor(c.r, c.g, c.b, c.a);
        }
    }
}
