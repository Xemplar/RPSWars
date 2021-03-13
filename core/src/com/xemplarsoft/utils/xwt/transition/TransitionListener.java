package com.xemplarsoft.utils.xwt.transition;

public interface TransitionListener {
    public void startedT();
    public void updatedT(float progress, final float duration);
    public void finishedT();
    
    class Adapter implements TransitionListener{
        public void startedT() { }
        public void updatedT(float progress, float duration) { }
        public void finishedT() { }
    }
}
