package com.xemplarsoft.games.cross.rps.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.xemplarsoft.games.cross.rps.sprite.Sprite;

public abstract class Entity {
    public static final float SPEED = 1F;
    protected float width, height, rot;
    protected Vector2 pos, vel;
    protected Sprite sprite;
    protected boolean dead = false;
    
    public Entity(Sprite sprite, float x, float y, float width, float height){
        this.sprite = sprite;
        this.pos = new Vector2(x, y);
        this.vel = new Vector2(0, 0);
        this.width = width;
        this.height = height;
    }
    
    public void setTarget(Vector2 dest){
        float dx = this.pos.x - dest.x;
        float dy = this.pos.y - dest.y;
        if(dx == 0 && dy == 0){
            vel = vel.scl(0);
            return;
        }
        if(dx == 0){
            vel.set(0, dy > 0 ? SPEED : -SPEED);
            return;
        }
        if(dy == 0){
            vel.set(dx > 0 ? SPEED : -SPEED, 0);
            return;
        }
        float offset = 0;
        if(dx < 0 && dy > 0) offset = 90;
        if(dx < 0 && dy < 0) offset = 180;
        if(dx > 0 && dy < 0) offset = 270;
        
        dx = Math.abs(dx);
        dy = Math.abs(dy);
        
        float dh = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        float theta = (float) Math.toDegrees(Math.asin(dy / dh));
        
        theta += offset;
        theta = (float)Math.toRadians(theta);
        
        vel.set((float)Math.cos(theta) * SPEED, (float)Math.sin(theta) * SPEED);
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *              Dest
     *     ◻        ◢◤◻
     *     ◻      ◢◤  ◻
     *  dy ◻  dh◢◤    ◻ opp = dy
     *     ◻  ◢◤      ◻
     *     ◻◢◤ θ    90◻
     *     ◻◻◻◻◻◻◻◻ ◻
     *  Me      dx         θ = sin-1(opp/dh)
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public float distance(Entity e){
        Vector2 diff = this.pos.cpy().sub(e.pos);
        return (float) Math.sqrt(Math.pow(diff.x, 2) + Math.pow(diff.y, 2));
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *     3    Bounds   4
     *     ◻◻◻◻◻◻◻◻◻◻◻
     *     ◻             ◻
     *     ◻     POS     ◻
     *     ◻      ◻     ◻
     *     ◻             ◻
     *     ◻             ◻
     *     ◻◻◻◻◻◻◻◻◻◻◻
     *     1             2
     *
     *     1: (x - width / 2, y - height / 2)
     *     2: (x + width / 2, y - height / 2)
     *     3: (x - width / 2, y + height / 2)
     *     4: (x + width / 2, y + height / 2)
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public Rectangle getFutureBounds(float delta){
        Vector2 pos = this.pos.cpy().mulAdd(vel, delta);
        return new Rectangle(pos.x - width / 2, pos.y - height / 2, width, height);
    }
    
    public Rectangle getBounds(){
        return new Rectangle(pos.x - width / 2, pos.y - height / 2, width, height);
    }
    
    public void kill(){
        this.dead = true;
    }
    
    public void update(float delta){
        pos = pos.mulAdd(vel, delta);
    }
    
    public boolean isDead(){
        return dead;
    }
    
    public boolean isCollidable(){
        return false;
    }
    
    public boolean isTouchable(){
        return false;
    }
    
    public void onTouch(Entity e){}
    
    public void render(SpriteBatch b){
        b.draw(sprite.getTex(), pos.x, pos.y, 0.5F, 0.5F, width, height, 1, 1, vel.angleDeg());
    }
}
