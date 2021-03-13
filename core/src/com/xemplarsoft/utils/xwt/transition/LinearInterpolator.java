package com.xemplarsoft.utils.xwt.transition;

public class LinearInterpolator extends AbstractInterpolator{
    public LinearInterpolator(boolean invert){
        super(invert);
    }
    
    public float getMultiplier() {
        return invert ? 1F - (pos / duration) : pos / duration;
    }
}
