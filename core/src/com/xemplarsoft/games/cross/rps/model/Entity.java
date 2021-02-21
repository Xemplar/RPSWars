package com.xemplarsoft.games.cross.rps.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected float width, height;
    protected Vector2 pos;
    protected TextureRegion tex;
    public Entity(TextureRegion region, float x, float y, float width, float height){
        this.tex = region;
        this.pos = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }
    
    public abstract void update(float delta);
    
    public void render(SpriteBatch b){
        b.draw(tex, pos.x, pos.y, width, height);
    }
}
