package com.xemplarsoft.games.cross.rps.model;

import com.xemplarsoft.games.cross.rps.sprite.Sprite;

public class BasicUnit extends Unit{
    public BasicUnit(Team t, float x, float y){
        super(t.getSprite(), x, y);
        setup(t);
    }
    
    public void update(float delta) {
        super.update(delta);
    }
    
    public void convert(Team team) {
        setup(team);
    }
    
    public String getName() {
        return team.getName() + ": Basic Unit";
    }
}
