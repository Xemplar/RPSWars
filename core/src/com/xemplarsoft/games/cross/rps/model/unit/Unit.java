package com.xemplarsoft.games.cross.rps.model.unit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.model.Entity;
import com.xemplarsoft.games.cross.rps.model.Team;
import com.xemplarsoft.games.cross.rps.model.World;
import com.xemplarsoft.games.cross.rps.screens.OptionsScreen;
import com.xemplarsoft.games.cross.rps.sprite.Sprite;

public abstract class Unit extends Entity {
    protected Team team;
    protected int maxHp = 2, hp = maxHp;
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
    
    public boolean isDead() {
        return hp == 0;
    }
    
    public void kill() {
        this.hp = 0;
    }
    
    public void onTouch(Entity e) {
        if(!(e instanceof Unit)) return;
        Unit p = (Unit) e;
        if(team.isPrey(p.team)){
            if(Boolean.parseBoolean(OptionsScreen.prefs[3])){
                this.convert(p.team);
            } else {
                //
                if(true){ //If people want health
                    if(!hit){
                        hit = true;
                        hp--;
                    }
                } else {
                    this.kill();
                }
            }
            if(Boolean.parseBoolean(OptionsScreen.prefs[0])) Wars.playSound(p.team);
        }
    }
    
    public Team getTeam(){
        return team;
    }
    
    public abstract void convert(Team team);
    public abstract String getName();
    
    private float hitDebounce = 0;
    private boolean hit = false;
    private final float MAX_HIT_DEBOUNCE = 2F;
    public void update(float delta, World w) {
        super.update(delta, w);
        if(hit){
            hitDebounce += delta;
            if(hitDebounce >= MAX_HIT_DEBOUNCE){
                hit = false;
                hitDebounce = 0;
            }
        }
    }
    
    public void render(SpriteBatch batch){
        if(!hit) {
            super.render(batch);
        } else {
            int time = (int)(hitDebounce * 30F);
            if(time % 10 > 5) super.render(batch);
        }
        
        float mid = (float)hp / maxHp * this.width;
        batch.draw(hp_plus(), pos.x + 0.1F, pos.y - 0.2F, mid, 0.2F);
        batch.draw(hp_minus(), pos.x + 0.1F + mid, pos.y - 0.2F, this.width - mid, 0.2F);
    }
    
    public static TextureRegion hp_plus(){
        return Wars.ur("hp", 1);
    }
    
    public static TextureRegion hp_minus(){
        return Wars.ur("hp", 0);
    }
}
