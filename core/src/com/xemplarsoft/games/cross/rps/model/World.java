package com.xemplarsoft.games.cross.rps.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class World {
    public Array<Entity> entities = new Array<>();
    
    public World(){
    
    }
    
    public void spawn(Entity e){
        entities.add(e);
    }
    
    public void despawn(Entity e){
        entities.removeValue(e, true);
    }
    
    public void render(SpriteBatch batch){
        for(Entity e : entities){
            e.render(batch);
        }
    }
    
    public void update(float delta){
        checkCollisions(delta);
        Array<Entity> killed = new Array<>();
        for(Entity e : entities){
            if(e.isDead()){
                killed.add(e);
                continue;
            }
            e.update(delta);
        }
        
        entities.removeAll(killed, true);
        killed.clear();
    }
    
    public void checkCollisions(float delta){
    
    }
}
