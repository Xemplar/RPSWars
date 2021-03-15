package com.xemplarsoft.games.cross.rps.scenario;

import com.xemplarsoft.utils.xwt.Panel;

public class Parameters {
    public float base_speed, chase_speed, run_speed;
    public TeamStats sta_rock, sta_paper, sta_scissor;
    public boolean convert;
    
    public static final Parameters getDefault(){
        Parameters ret = new Parameters();
        
        ret.base_speed = 1F;
        ret.chase_speed = 1F;
        ret.run_speed = 1F;
        ret.convert = false;
        ret.sta_rock = new TeamStats(1, 0, 1, 10, 1F);
        ret.sta_paper = new TeamStats(1, 0, 1, 10, 1F);
        ret.sta_scissor = new TeamStats(1, 0, 1, 10, 1F);
        
        return ret;
    }
    
    public static final class TeamStats{
        public int attack, defense, hp, units;
        public float speed = 1F;
        public TeamStats(int attack, int defense, int hp, int units){
            this.attack = attack;
            this.defense = defense;
            this.hp = hp;
            this.units = units;
        }
        
        public TeamStats(int attack, int defense, int hp, int units, float speed){
            this(attack, defense, hp, units);
            this.speed = speed;
        }
    }
}
