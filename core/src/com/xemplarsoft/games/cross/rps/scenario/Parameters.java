package com.xemplarsoft.games.cross.rps.scenario;

import com.xemplarsoft.utils.xwt.Panel;

public class Parameters {
    public float base_speed, chase_speed, run_speed;
    public boolean convert;
    
    public static final Parameters getDefault(){
        Parameters ret = new Parameters();
        
        return ret;
    }
}
