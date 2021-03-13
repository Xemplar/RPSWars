package com.xemplarsoft.games.cross.rps.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.xemplarsoft.games.cross.rps.screens.TitleScreen;
import com.xemplarsoft.utils.xwt.AbstractElement;

public class BounceBG extends AbstractElement {
    private Vector2 vel, pos;
    private TextureRegion bg;
    public BounceBG(TextureRegion bg, float width, float height){
        this.bg = bg;
        this.width = width;
        this.height = height;
        pos = new Vector2(0, 0);
        vel = new Vector2(1, 1);
    }

    public int getZ() {
        return -1;
    }

    public void update(float delta) {
        Vector2 future = pos.cpy().add(vel.cpy().scl(delta));

        if(future.x > 0) vel = new Vector2(-1, vel.y);
        if(future.y > 0) vel = new Vector2(vel.x, -1);
        if(future.x < -(width - TitleScreen.CAM_WIDTH)) vel = new Vector2(1, vel.y);
        if(future.y < -(height - TitleScreen.CAM_HEIGHT)) vel = new Vector2(vel.x, 1);

        this.pos.x += vel.x * delta;
        this.pos.y += vel.y * delta;
    }
    
    @Override
    public void render(SpriteBatch b) {
        Color c = b.getColor();
        b.setColor(c.r, c.g, c.b, 1);
        b.draw(bg, pos.x, pos.y, width, height);
        b.setColor(c);
    }
}
