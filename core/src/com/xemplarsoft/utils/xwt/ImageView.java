package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageView extends AbstractComponent {
    protected TextureRegion image;

    public ImageView(float x, float y, float width, float height, TextureRegion image){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public void render(SpriteBatch batch) {
        if(visible) {
            if (border != null) border.render(batch, this);
            batch.draw(image, x, y, width, height);
        }
    }
}
