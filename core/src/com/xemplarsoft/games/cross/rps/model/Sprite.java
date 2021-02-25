package com.xemplarsoft.games.cross.rps.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Sprite {
    private TextureRegion tex;
    public Sprite(TextureRegion region){
        this.tex = region;
    }
    protected Sprite(){}
    
    public TextureRegion getTex(){
        return tex;
    }
}
