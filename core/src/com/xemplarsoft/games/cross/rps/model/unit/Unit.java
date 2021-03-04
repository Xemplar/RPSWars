package com.xemplarsoft.games.cross.rps.model.unit;

import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.model.Entity;
import com.xemplarsoft.games.cross.rps.model.Team;
import com.xemplarsoft.games.cross.rps.model.World;
import com.xemplarsoft.games.cross.rps.sprite.Sprite;

public abstract class Unit extends Entity {
    protected Team team;
    public Unit(Sprite sprite, float x, float y){
        super(sprite, x, y, 1.25F, 1.25F);
    }
    
    public void setup(Team team){
        this.team = team;
        this.sprite = team.getSprite();
    }
    
    public boolean isTouchable() {
        return true;
    }
    public boolean isCollidable() {
        return true;
    }
    
    public void onTouch(Entity e) {
        if(!(e instanceof Unit)) return;
        Unit p = (Unit) e;
        if(team.isPrey(p.team)){
            this.convert(p.team);
            Wars.playSound(p.team);
        }
    }
    
    public Team getTeam(){
        return team;
    }
    
    public abstract void convert(Team team);
    public abstract String getName();
    
    public void update(float delta, World w) {
        super.update(delta, w);
    }
}
