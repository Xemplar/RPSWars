package com.xemplarsoft.games.cross.rps.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.xemplarsoft.games.cross.rps.controller.AIController;
import com.xemplarsoft.games.cross.rps.controller.CollisionController;
import com.xemplarsoft.games.cross.rps.controller.Controller;
import com.xemplarsoft.games.cross.rps.controller.ai.AI;
import com.xemplarsoft.games.cross.rps.controller.ai.AbstractAI;
import com.xemplarsoft.games.cross.rps.sprite.Sprite;

public abstract class Entity {
    public static final com.xemplarsoft.games.cross.rps.controller.Controller DEFAULT_CONTROLLER = new CollisionController();
    public static long ENTITY_COUNTER = 0;
    public static final float SPEED = 2.5F;
    protected Sprite sprite;
    protected boolean dead = false;
    
    public float width, height, rot;
    public Vector2 pos, vel;
    
    protected Vector2 target;
    protected Controller controller;
    public final long ID;
    
    public Entity(Sprite sprite, float x, float y, float width, float height){
        ID = ENTITY_COUNTER++;
        this.sprite = sprite;
        this.pos = new Vector2(x, y);
        this.vel = new Vector2(0, 0);
        this.width = width;
        this.height = height;
        
        target = new Vector2(x, y);
        this.controller = DEFAULT_CONTROLLER;
    }
    
    public void setController(Controller controller){
        this.controller = controller;
    }
    
    public void setAI(AI ai){
        this.controller = new AIController(ai, this);
    }
    
    public boolean isIdle(){
        if(this.controller instanceof AIController) return ((AIController) this.controller).isIdle();
        return false;
    }
    
    protected void provisionTask(String name, Object... args){
        if(this.controller instanceof AIController) ((AIController) this.controller).provisionTask(name, args);
    }
    
    public void setTarget(Vector2 dest) {
        this.target = dest;
    }
    
    public void updateVelocity(float delta){
        if(target.equals(pos)) return;
        
        Vector2 dest = target.cpy();
        
        float dx = this.pos.x - dest.x;
        float dy = this.pos.y - dest.y;
        if(Math.abs(dx) <= 0.001F && Math.abs(dy) <= 0.001F){
            vel = vel.scl(0);
            return;
        }
        
        float dh = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        float thetaS = (float) Math.asin(dy / dh);
        float thetaC = (float) Math.acos(dx / dh);
        
        vel.set(-(float)Math.cos(thetaC) * SPEED, -(float)Math.sin(thetaS) * SPEED);
    
        float diff = Math.abs(rot - vel.angleDeg());
        if(rot < vel.angleDeg() && diff > 5) rot += delta * 15F;
        if(rot > vel.angleDeg() && diff > 5) rot -= delta * 15F;
    }
    
    /*    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *
                    Dest
     *     ◻        ◢◤◻
           ◻      ◢◤  ◻
     *  dy ◻  dh◢◤    ◻ opp = dy
           ◻  ◢◤      ◻ adj = dx
     *     ◻◢◤ θ    90◻
           ◻◻◻◻◻◻◻◻
     *  Me      dx         θs = sin-1(opp/dh)
                           θc = cos-1(adj/dh)
     *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    */
    
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
    
    public void update(float delta, World w){
        sprite.update(delta);
        updateVelocity(delta);
        controller.update(delta, this, w);
        
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
        b.draw(sprite.getTex(), pos.x, pos.y, 0.5F, 0.5F, width, height, 1, 1, rot);
    }
}
