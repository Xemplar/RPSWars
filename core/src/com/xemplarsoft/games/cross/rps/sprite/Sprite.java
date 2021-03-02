package com.xemplarsoft.games.cross.rps.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static com.xemplarsoft.games.cross.rps.screens.GameScreen.$_GLOBAL_CLOCK;

public class Sprite {
    private TextureRegion tex;
    public Sprite(TextureRegion region){
        this.tex = region;
    }
    protected Sprite(){}
    
    protected long prev;
    public void update(float delta){
    
    }
    
    public TextureRegion getTex(){
        return tex;
    }
}
