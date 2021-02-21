package com.xemplarsoft.games.cross.rps.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.xemplarsoft.games.cross.rps.screens.MenuScreen;

public class BounceBG extends Entity{
    private Vector2 vel;
    public BounceBG(TextureRegion bg, float width, float height){
        super(bg, 0, 0, width, height);
        vel = new Vector2(1, 1);
    }

    public int getZ() {
        return -1;
    }

    public void update(float delta) {
        Vector2 future = pos.cpy().add(vel.cpy().scl(delta));

        if(future.x > 0) vel = new Vector2(-1, vel.y);
        if(future.y > 0) vel = new Vector2(vel.x, -1);
        if(future.x < -(width - MenuScreen.CAM_WIDTH)) vel = new Vector2(1, vel.y);
        if(future.y < -(height - MenuScreen.CAM_HEIGHT)) vel = new Vector2(vel.x, 1);

        this.pos.x += vel.x * delta;
        this.pos.y += vel.y * delta;
    }
}
