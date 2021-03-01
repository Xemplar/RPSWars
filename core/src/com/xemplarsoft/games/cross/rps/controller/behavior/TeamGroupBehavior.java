package com.xemplarsoft.games.cross.rps.controller.behavior;

import com.xemplarsoft.games.cross.rps.model.Entity;
import com.xemplarsoft.games.cross.rps.model.World;
import com.xemplarsoft.games.cross.rps.model.unit.Unit;

public class TeamGroupBehavior implements Behavior{
    protected boolean execute = false;
    
    /*
     * Args Are Max, then Min
     */
    public boolean execute(Entity e, float delta, World w, Object... args) {
        if(!execute) return true;
    
        Entity closest = null;
        float d = Float.MAX_VALUE;
        
        for(int i = 0; i < w.entities.size; i++) {
            Entity curr = w.entities.get(i);
            if(e == curr) continue;
            if(!(curr instanceof Unit)) continue;
            Unit u = (Unit) curr;
            
            if(u.getTeam() != ((Unit)e).getTeam()) continue;
            
            float dist = w.entities.get(i).pos.dst(e.pos);
            if(dist < (Integer.parseInt(args[0].toString())) && dist > (Integer.parseInt(args[1].toString())) && d > dist){
                d = dist;
                closest = w.entities.get(i);
            }
        }
        if(closest == null) return true;
        e.setTarget(closest.pos);
        
        return false;
    }
}
