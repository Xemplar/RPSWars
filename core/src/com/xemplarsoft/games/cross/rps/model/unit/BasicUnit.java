package com.xemplarsoft.games.cross.rps.model.unit;

import com.xemplarsoft.games.cross.rps.model.Team;
import com.xemplarsoft.games.cross.rps.model.World;
import com.xemplarsoft.games.cross.rps.model.unit.Unit;

public class BasicUnit extends Unit {
    public BasicUnit(Team t, float x, float y){
        super(t.getSprite(), x, y);
        setup(t);
    }
    
    public void update(float delta, World w) {
        super.update(delta, w);
    }
    
    public void convert(Team team) {
        setup(team);
    }
    
    public String getName() {
        return team.getName() + ": Basic Unit";
    }
}
