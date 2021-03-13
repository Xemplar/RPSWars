package com.xemplarsoft.games.cross.rps.scenario;

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
    
    public static ParameterFetcher getInstance() {
        return instance;
    }
    
    public static ParameterFetcher getInstance(boolean initialize) {
        if(initialize) instance.setParams(Parameters.getDefault());
        return instance;
    }
}
