package com.xemplarsoft.games.cross.rps.controller.ai;

import com.badlogic.gdx.utils.Queue;

public abstract class ThreadedAbstractAI implements AI, Runnable{
    protected Queue<Task> tasks = new Queue<>();
    public Thread t;
    public volatile boolean running;
    public ThreadedAbstractAI(){
        t = new Thread(this);
    }
    
    public synchronized void wakeup(){
        t.notify();
    }
    
    public void run(){
        while(running){
            if(!loop()){
                try {
                    t.wait();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    public abstract boolean loop();
}
