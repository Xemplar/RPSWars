package com.xemplarsoft.games.cross.rps.controller.behavior;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Segment;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.math.Segment2D;
import com.xemplarsoft.games.cross.rps.model.Entity;
import com.xemplarsoft.games.cross.rps.model.World;
import com.xemplarsoft.games.cross.rps.model.unit.Unit;

import static com.xemplarsoft.games.cross.rps.screens.GameScreen.CAM_HEIGHT;
import static com.xemplarsoft.games.cross.rps.screens.GameScreen.CAM_WIDTH;

public class AvoidPredatorBehavior implements Behavior{
    
    protected boolean isRunning;
    protected Entity from;
    protected Vector2 bestVel;
    public boolean execute(Entity e, float delta, World w, Object... args) {
        Entity closest = null;
        Entity pair = null;
        float d = Float.MAX_VALUE;
        
        for(int i = 0; i < w.entities.size; i++) {
            Entity curr = w.entities.get(i);
            if(e == curr) continue;
            if(!(curr instanceof Unit)) continue;
            Unit u = (Unit) curr;
            
            if(u.getTeam().predator != ((Unit)e).getTeam().id) continue;
            
            float dist = w.entities.get(i).pos.dst(e.pos);
            if(dist < (Integer.parseInt(args[0].toString())) && d > dist){
                d = dist;
                pair = closest;
                closest = w.entities.get(i);
            }
        }
        if(closest == null) return true;
        
        if(pair == null) {
            float dx = (e.pos.x - closest.pos.x) * 1;
            float dy = (e.pos.y - closest.pos.y) * 1;
            e.setTarget(new Vector2(e.pos.x + dx, e.pos.y + dy), Wars.PARAMS.getRunSpeed());
        } else {
            Segment2D death = new Segment2D(closest.pos, pair.pos);
            Vector2 mid = death.midpoint();
            Segment2D escape = death.bisector(Math.max(e.pos.dst(mid) * 3, 5F) / (death.length() != 0 ? death.length() : 0.0001F));
            
            w.debug.setColor(0, 1, 0, 1);
            //w.debug.line(death.a, death.b);
            w.debug.setColor(0, 1, 1, 1);
            //w.debug.line(escape.a, escape.b);
            if(mid.dst(e.pos) < mid.dst(closest.pos)) {
                if(escape.a.x < 0 || escape.a.x > CAM_WIDTH || escape.a.y < 0 || escape.a.y > CAM_HEIGHT) {
                    e.setTarget(escape.b, Wars.PARAMS.getRunSpeed());
                    isRunning = true;
                    from = closest;
                    bestVel = e.vel;
                    return false;
                }
                if(escape.b.x < 0 || escape.b.x > CAM_WIDTH || escape.b.y < 0 || escape.b.y > CAM_HEIGHT) {
                    e.setTarget(escape.a, Wars.PARAMS.getRunSpeed());
                    isRunning = true;
                    from = closest;
                    bestVel = e.vel;
                    return false;
                }
            }
            if(escape.a.dst(e.pos) > escape.b.dst(e.pos)) e.setTarget(escape.b, Wars.PARAMS.getRunSpeed());
            else e.setTarget(escape.a, Wars.PARAMS.getRunSpeed());
        }
        return false;
    }
}
