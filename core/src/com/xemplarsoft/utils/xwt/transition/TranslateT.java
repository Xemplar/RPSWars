package com.xemplarsoft.utils.xwt.transition;

import com.badlogic.gdx.math.Vector2;
import com.xemplarsoft.utils.xwt.AbstractComponent;

public class TranslateT extends AbstractTransition{
    protected Vector2 to, diff;
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
        
        float ration = 1F - interpolator.getMultiplier(); //Comes to positive 1 but we want the inverse ration.
        comp.setPosition(to.x - diff.x * ration, to.y - diff.y * ration);
    }
    
    public void start() {
        super.start();
        pro = 0;
    }
    
    public void registerComponent(AbstractComponent comp) {
        super.registerComponent(comp);
        this.diff = comp.getPosition().sub(to);
    }
}
