package com.xemplarsoft.utils.xwt.transition;

import com.badlogic.gdx.math.Vector2;
import com.xemplarsoft.utils.xwt.AbstractComponent;

public class TranslateT extends AbstractTransition{
    protected Vector2 to, start, diff;
    protected float pro;
    public TranslateT(Vector2 to, float duration){
        super(duration);
        this.to = to;
    }
    
    public void update(float delta) {
        super.update(delta);
        if(!run) return;
        
        pro += delta;
        if(pro >= duration) {
            comp.setPosition(to);
            run = false;
            return;
        }
        
        float ration = interpolator.getMultiplier();
        comp.setPosition(start.x - diff.x * ration, start.y - diff.y * ration);
    }
    
    public void start() {
        super.start();
        pro = 0;
    }
    
    public void registerComponent(AbstractComponent comp) {
        super.registerComponent(comp);
        this.start = comp.getPosition();
        this.diff = start.cpy().sub(to.cpy());
    }
}
