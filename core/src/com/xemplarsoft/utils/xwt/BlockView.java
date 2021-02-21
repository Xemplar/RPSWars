package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BlockView extends AbstractElement {
    protected TextureRegion tex;

    public BlockView(TextureRegion tex, float x, float y, float w, float h){
        this.tex = tex;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public void render(SpriteBatch batch) {
        if(visible) {
            batch.draw(tex, x, y, width, height);
        }
    }
}
