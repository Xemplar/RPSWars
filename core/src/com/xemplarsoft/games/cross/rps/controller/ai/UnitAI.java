package com.xemplarsoft.games.cross.rps.controller.ai;

import com.badlogic.gdx.math.Vector2;
import com.xemplarsoft.games.cross.rps.controller.behavior.ChasePreyBehavior;
import com.xemplarsoft.games.cross.rps.controller.behavior.TeamGroupBehavior;
import com.xemplarsoft.games.cross.rps.model.World;

import static com.xemplarsoft.games.cross.rps.screens.GameScreen.CAM_HEIGHT;
import static com.xemplarsoft.games.cross.rps.screens.GameScreen.CAM_WIDTH;

public class UnitAI extends AbstractAI{
    protected TeamGroupBehavior b1;
    protected ChasePreyBehavior b2;
    
    public UnitAI(){
        b1 = new TeamGroupBehavior();
        b2 = new ChasePreyBehavior();
    }
    
    public boolean handleTask(Task t, float delta, World w) {
        e.setTarget(new Vector2(CAM_WIDTH / 2, CAM_HEIGHT / 2));
        if(t.getName().equals("group")) return b1.execute(e, delta, w, t.getArgs());
        if(t.getName().equals("attack")) return b2.execute(e, delta, w, t.getArgs());
        return true;
    }
}
