package com.xemplarsoft.games.cross.rps.controller.behavior;

import com.xemplarsoft.games.cross.rps.model.Entity;
import com.xemplarsoft.games.cross.rps.model.World;
import com.xemplarsoft.games.cross.rps.model.unit.Unit;

public class ChasePreyBehavior implements Behavior{
    
    public boolean execute(Entity e, float delta, World w, Object... args) {
        Entity closest = null;
        float d = Float.MAX_VALUE;
        
        for(int i = 0; i < w.entities.size; i++) {
            Entity curr = w.entities.get(i);
            if(e == curr) continue;
            if(!(curr instanceof Unit)) continue;
            Unit u = (Unit) curr;
            
            if(u.getTeam().prey != ((Unit)e).getTeam().id) continue;
            
            float dist = w.entities.get(i).pos.dst(e.pos);
            if(dist < (Integer.parseInt(args[0].toString())) && d > dist){
                d = dist;
                closest = w.entities.get(i);
            }
        }
        if(closest == null) return true;
        e.setTarget(closest.pos);
        
        return false;
    }
}
