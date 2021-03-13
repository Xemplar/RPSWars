package com.xemplarsoft.utils.xwt.transition;

public class HalfSinInterpolator extends AbstractInterpolator{
    public HalfSinInterpolator(boolean invert){
        super(invert);
    }
    
    public float getMultiplier() {
        float ration = pos / duration;
        if(invert) ration = 1F - ration;
        return (float)Math.sin(ration * Math.PI / 2);
    }
}
