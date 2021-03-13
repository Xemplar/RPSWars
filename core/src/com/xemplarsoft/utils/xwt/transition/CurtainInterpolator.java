package com.xemplarsoft.utils.xwt.transition;

public class CurtainInterpolator extends AbstractInterpolator{
    public CurtainInterpolator(boolean invert) {
        super(invert);
    }
    
    public float getMultiplier() {
        float x = pos / duration;
        float y = 2 * x * (x - 0.5F);
        return invert ? 1F - y : y;
    }
}
