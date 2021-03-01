package com.xemplarsoft.games.cross.rps.controller.ai;

import com.badlogic.gdx.utils.Queue;
import com.xemplarsoft.games.cross.rps.model.Entity;
import com.xemplarsoft.games.cross.rps.model.World;

public abstract class AbstractAI implements AI{
    protected Queue<Task> tasks = new Queue<>();
    protected Entity e;
    protected boolean idle = true;
    
    public AbstractAI(){}
    
    public void provisionTask(String name, Object... args) {
        tasks.addLast(new Task(name, args));
    }
    
    public boolean isRegistered() {
        return this.e != null;
    }
    
    public boolean isIdle() {
        return idle;
    }
    
    public void register(Entity e) {
        this.e = e;
    }
    
    public void update(float delta, World w) {
        idle = tasks.isEmpty();
        if(idle) return;
        
        if(handleTask(tasks.first(), delta, w)) tasks.removeFirst();
    }
    
    public abstract boolean handleTask(Task t, float delta, World w);
}
