package com.xemplarsoft.games.cross.rps.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import static com.xemplarsoft.games.cross.rps.screens.GameScreen.$_GLOBAL_CLOCK;

public class SpriteA extends Sprite{
    protected Array<Sprite> sprites = new Array<>();
    protected final int frameCount;
    protected float iv, im;
    protected int frame;
    
    public SpriteA(float iv, Sprite... sprites){
        this.iv = iv;
        this.sprites.addAll(sprites);
        this.frameCount = sprites.length;
    }
    
    public SpriteA(float iv, TextureRegion... regions){
        this.iv = iv;
        Sprite[] sprites = new Sprite[regions.length];
        for(int i = 0; i < regions.length; i++){
            sprites[i] = new Sprite(regions[i]);
        }
        this.sprites.addAll(sprites);
        this.frameCount = sprites.length;
    }
    
    public void update(float delta) {
        if($_GLOBAL_CLOCK == prev) return;
        prev = $_GLOBAL_CLOCK;
        
        this.im += delta;
        if(im >= iv){
            im -= iv;
            frame++;
            if(frame >= frameCount) frame = 0;
        }
        sprites.get(frame).update(delta);
    }
    
    public TextureRegion getTex() {
        return sprites.get(frame).getTex();
    }
}
