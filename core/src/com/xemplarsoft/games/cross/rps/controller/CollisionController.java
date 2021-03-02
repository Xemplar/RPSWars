package com.xemplarsoft.games.cross.rps.controller;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.xemplarsoft.games.cross.rps.model.Entity;
import com.xemplarsoft.games.cross.rps.model.World;

import static com.xemplarsoft.games.cross.rps.screens.GameScreen.CAM_HEIGHT;
import static com.xemplarsoft.games.cross.rps.screens.GameScreen.CAM_WIDTH;

public class CollisionController implements Controller {
    public void update(float delta, Entity e, World w) {
        Rectangle f = e.getFutureBounds(delta);
        if(f.x < 0 && e.vel.x < 0) {
            e.vel.x = 0;
            e.vel.y = e.vel.y < 0 ? -1 : 1;
        }
        if(f.x >= ((CAM_WIDTH - 1F) - f.width) && e.vel.x > 0){
            e.vel.x = 0;
            e.vel.y = e.vel.y < 0 ? -1 : 1;
        }
    
        if(f.y < 0 && e.vel.y < 0){
            e.vel.y = 0;
            if(e.vel.x != 0) e.vel.x = e.vel.x < 0 ? -1 : 1;
        }
        if(f.y >= ((CAM_HEIGHT - 1F) - f.height) && e.vel.y > 0){
            e.vel.y = 0;
            if(e.vel.x != 0) e.vel.x = e.vel.x < 0 ? -1 : 1;
        }
        
        Array<Entity> close = new Array<>();
    
        boolean colX = false, colY = false;
    
        for(int i = 0; i < w.entities.size; i++) {
            if(e == w.entities.get(i)) continue;
            
            float dist = w.entities.get(i).pos.dst(e.pos);
            if(dist < 2F) close.add(w.entities.get(i));
        }
        
        if(close.size == 0) return;
        
        for(int i = 0; i < close.size; i++) {
            Entity curr = close.get(i);
            if(curr.getFutureBounds(delta).overlaps(f)){
                if(curr.isTouchable()) curr.onTouch(e);
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
    
        if(!e.isCollidable()) return;
        e.vel.scl(colX ? 0 : 1, colY ? 0 : 1);
    }
}
