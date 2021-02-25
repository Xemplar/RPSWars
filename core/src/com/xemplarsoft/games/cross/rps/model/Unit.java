package com.xemplarsoft.games.cross.rps.model;

import com.xemplarsoft.games.cross.rps.sprite.Sprite;

public abstract class Unit extends Entity{
    protected Team team;
    public Unit(Sprite sprite, float x, float y){
        super(sprite, x, y, 1, 1);
    }
    
    public void setup(Team team){
        this.team = team;
        this.sprite = team.getSprite();
    }
    
    public boolean isTouchable() {
        return true;
    }
    
    public void onTouch(Entity e) {
        if(!(e instanceof Unit)) return;
        Unit p = (Unit) e;
        if(team.isPrey(p.team)){
            p.convert(team);
        }
    }
    
    public Team getTeam(){
        return team;
    }
    
    public abstract void convert(Team team);
    public abstract String getName();
    
    public void update(float delta) {
        super.update(delta);
    }
}
