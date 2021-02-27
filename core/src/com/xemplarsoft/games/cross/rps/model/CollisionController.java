package com.xemplarsoft.games.cross.rps.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class CollisionController implements Controller {
    public void update(float delta, Entity e, World w) {
        if(!e.isCollidable()) return;
        
        Array<Entity> close = new Array<>();
    
        boolean colX = false, colY = false;
    
        for(int i = 0; i < w.entities.size; i++) {
            if(e == w.entities.get(i)) continue;
            
            float dist = w.entities.get(i).pos.dst(e.pos);
            if(dist < 2F) close.add(w.entities.get(i));
        }
        if(close.size == 0) return;
    
        Rectangle f = e.getFutureBounds(delta);
        for(int i = 0; i < close.size; i++) {
            Entity curr = close.get(i);
            if(curr.getFutureBounds(delta).overlaps(f)){
                if(!colX && !(
                    (e.vel.x > 0 && e.pos.x > curr.pos.x) ||
                    (e.vel.x < 0 && e.pos.x < curr.pos.x))
                ) colX = true;
                
                if(!colY && !(
                    (e.vel.y > 0 && e.pos.y > curr.pos.y) ||
                    (e.vel.y < 0 && e.pos.y < curr.pos.y))
                ) colY = true;
                
                if(colX && colY) break;
            }
        }
    
        e.vel.scl(colX ? 0 : 1, colY ? 0 : 1);
    }
}
