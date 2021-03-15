package com.xemplarsoft.games.cross.rps.scenario;

import com.xemplarsoft.games.cross.rps.model.Team;
import org.omg.Dynamic.Parameter;

public final class ParameterFetcher {
    private static ParameterFetcher instance = new ParameterFetcher();
    private Parameters params;
    private ParameterFetcher(){}
    
    public float getMovementSpeed(){
        return params.base_speed;
    }
    
    public float getChaseSpeed(){
        return params.chase_speed;
    }
    
    public float getRunSpeed(){
        return params.run_speed;
    }
    
    public boolean convert(){
        return params.convert;
    }
    
    public void setParams(Parameters params){
        this.params = params;
    }
    public Parameters getParams(){
        return this.params;
    }
    
    public int getAttack(Team t){
        switch(t){
            default: return this.params.sta_scissor.attack;
            case B: return this.params.sta_paper.attack;
            case C: return this.params.sta_rock.attack;
        }
    }
    
    public int getDefense(Team t){
        switch(t){
            default: return this.params.sta_scissor.defense;
            case B: return this.params.sta_paper.defense;
            case C: return this.params.sta_rock.defense;
        }
    }
    
    public int getMaxHP(Team t){
        switch(t){
            default: return this.params.sta_scissor.hp;
            case B: return this.params.sta_paper.hp;
            case C: return this.params.sta_rock.hp;
        }
    }
    
    public int getUnitCount(Team t){
        switch(t){
            default: return this.params.sta_scissor.units;
            case B: return this.params.sta_paper.units;
            case C: return this.params.sta_rock.units;
        }
    }
    
    public float getSpeed(Team t){
        switch(t){
            default: return this.params.sta_scissor.speed;
            case B: return this.params.sta_paper.speed;
            case C: return this.params.sta_rock.speed;
        }
    }
    
    public static ParameterFetcher getInstance() {
        return instance;
    }
    
    public static ParameterFetcher getInstance(boolean initialize) {
        if(initialize) instance.setParams(Parameters.getDefault());
        return instance;
    }
}
