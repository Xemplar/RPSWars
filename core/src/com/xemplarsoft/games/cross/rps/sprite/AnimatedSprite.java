package com.xemplarsoft.games.cross.rps.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimatedSprite extends Sprite{
    protected Array<TextureRegion> regions = new Array<>();
    protected final int frameCount;
    protected float iv, im;
    protected int frame;
    
    public AnimatedSprite(float iv, TextureRegion... regions){
        this.iv = iv;
        this.regions.addAll(regions);
        this.frameCount = regions.length;
    }
    
    public void update(float delta) {
        this.im += delta;
        if(im >= iv){
            im -= iv;
            frame++;
            if(frame >= frameCount) frame = 0;
        }
    }
    
    public TextureRegion getTex() {
        return regions.get(frame);
    }
}
