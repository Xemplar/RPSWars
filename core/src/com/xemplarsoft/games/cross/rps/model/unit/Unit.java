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
    protected int maxHp, hp, attack, defense;
    protected float team_speed;
    
    public Unit(Sprite sprite, float x, float y){
        super(sprite, x, y, 1.25F, 1.25F);
    }
    
    public void setup(Team team){
        this.team = team;
        this.sprite = team.getSprite();
        this.maxHp = this.hp = Wars.PARAMS.getMaxHP(team);
        this.attack = Wars.PARAMS.getAttack(team);
        this.defense = Wars.PARAMS.getDefense(team);
        this.team_speed = Wars.PARAMS.getSpeed(team);
    }
    
    protected float getSpeedMultiplier() {
        return super.getSpeedMultiplier() * team_speed;
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
            if(Wars.PARAMS.convert()){
                this.convert(p.team);
            } else {
                if(!hit){
                    hitBy(p);
                }
            }
        }
    }
    
    private void hitBy(Unit u){
        this.hp = Math.max(this.hp - Math.max(u.attack - this.defense, 0), 0);
        System.out.println("My HP: " + this.hp + ", Attack: " + u.attack + ", Defense: " + this.defense);
        if(Boolean.parseBoolean(OptionsScreen.prefs[0])) Wars.playSound(u.team);
        hit = true;
    }
    
    public Team getTeam(){
        return team;
    }
    
    public abstract void convert(Team team);
    public abstract String getName();
    
    private float hitDebounce = 0;
    private boolean hit = false;
    private final float MAX_HIT_DEBOUNCE = 0.5F;
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
        
        //if(maxHp == 1) return;
        
        float mid = (float)hp / maxHp * this.width;
        batch.draw(hp_plus(), pos.x, pos.y - 0.2F, mid, 0.2F);
        batch.draw(hp_minus(), pos.x + mid, pos.y - 0.2F, this.width - mid, 0.2F);
    }
    
    public static TextureRegion hp_plus(){
        return Wars.ur("hp", 1);
    }
    
    public static TextureRegion hp_minus(){
        return Wars.ur("hp", 0);
    }
    
    public enum STAT{
        ATTACK,
        DEFENSE,
        MAX_HP
    }
}
