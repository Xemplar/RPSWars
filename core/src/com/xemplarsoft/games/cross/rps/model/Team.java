package com.xemplarsoft.games.cross.rps.model;

import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.games.cross.rps.sprite.Sprite;
import com.xemplarsoft.games.cross.rps.sprite.SpriteA;

import static com.xemplarsoft.games.cross.rps.Wars.*;

public enum Team {
    A(A_ID, B_ID, C_ID, "scissors", "Scissors"),
    B(B_ID, C_ID, A_ID, "paper", "Paper"),
    C(C_ID, A_ID, B_ID, "rock", "Rock");
    
    public final int id, prey, predator;
    public final String prefix, name;
    Team(int id, int predator, int prey, String prefix, String name){
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
        switch(id){
            default: return S_SCI;
            case B_ID: return S_PAP;
            case C_ID: return S_ROC;
        }
    }
    
    public static Team fromID(int id){
        for(Team t : Team.values()){
            if(t.id == id) return t;
        }
        return A;
    }
}
