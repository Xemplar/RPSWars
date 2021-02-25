package com.xemplarsoft.games.cross.rps.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Entity{
    public Player predator, prey;
    public Player(TextureRegion region, float x, float y){
        super(new Sprite(region), x, y, 1, 1);
    }
    
    public void setup(Player predator, Player prey){
        this.predator = predator;
        this.prey = prey;
    }
    
    
    public void update(float delta) {
        super.update(delta);
    }
}
