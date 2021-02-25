package com.xemplarsoft.games.cross.rps.model;

import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.sprite.Sprite;

public enum Team {
    A(1, 2, 3, "scissors", "Scissors"),
    B(2, 3, 1, "paper", "Paper"),
    C(3, 1, 2, "rock", "Rock");
    
    public final int id, prey, predator;
    public final String prefix, name;
    Team(int id, int prey, int predator, String prefix, String name){
        this.id = id;
        this.prey = prey;
        this.predator = predator;
        
        this.prefix = prefix;
        this.name = name;
    }
    
    public boolean isPrey(Team t){
        return this.prey == t.id;
    }
    
    public String getName(){
        return name;
    }
    
    public Sprite getSprite(){
        return new Sprite(Wars.pr("player" + id));
    }
    
    public static Team fromID(int id){
        for(Team t : Team.values()){
            if(t.id == id) return t;
        }
        return null;
    }
}
