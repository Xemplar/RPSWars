package com.xemplarsoft.games.cross.rps.controller;

import com.xemplarsoft.games.cross.rps.controller.ai.AI;
import com.xemplarsoft.games.cross.rps.model.Entity;
import com.xemplarsoft.games.cross.rps.model.World;
import com.xemplarsoft.games.cross.rps.model.unit.Unit;

/**
 * Class must be used exclusively with one entity per instance.
 */
public class AIController extends CollisionController{
    protected AI ai;
    public AIController(AI ai, Entity u) {
        this.ai = ai;
        this.ai.register(u);
    }
    
    public boolean isIdle(){
        return ai.isIdle();
    }
    
    public void provisionTask(String name, Object... args){
        this.ai.provisionTask(name, args);
    }
    
    public void update(float delta, Entity e, World w) {
        this.ai.update(delta, w);
        
        // Must always abide by collision detection, super method must be called last.
        super.update(delta, e, w);
    }
}
