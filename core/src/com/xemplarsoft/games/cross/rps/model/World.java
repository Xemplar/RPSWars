package com.xemplarsoft.games.cross.rps.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.xemplarsoft.games.cross.rps.controller.ai.Task;
import com.xemplarsoft.games.cross.rps.model.unit.Unit;

import static com.xemplarsoft.games.cross.rps.Wars.*;

public class World {
    public Array<Entity> entities = new Array<>();
    public Array<Task> TASKS_A, TASKS_B, TASKS_C;
    public ShapeRenderer debug;
    
    public World(){
        TASKS_A = new Array<>();
        TASKS_B = new Array<>();
        TASKS_C = new Array<>();
    
        TASKS_A.add(new Task("group", 5, 3), new Task("attack", 6));
        TASKS_B.add(new Task("group", 5, 3), new Task("attack", 6));
        TASKS_C.add(new Task("group", 5, 3), new Task("attack", 6));
    }
    
    public void spawn(Entity e){
        entities.add(e);
    }
    
    public void despawn(Entity e){
        entities.removeValue(e, true);
    }
    
    public void clear(){
        entities.clear();
    }
    
    public void render(SpriteBatch batch){
        for(Entity e : entities){
            e.render(batch);
        }
    }
    
    public void update(float delta, ShapeRenderer debug){
        if(this.debug != debug) this.debug = debug;
        refreshTeamTasks();
        
        Array<Entity> killed = new Array<>();
        for(Entity e : entities){
            if(e.isDead()){
                killed.add(e);
                continue;
            }
            e.update(delta, this);
        }
        for(Entity e : killed){
            if(!(e instanceof Unit)) continue;
            Unit p = (Unit) e; p.kill();
        }
        entities.removeAll(killed, true);
        killed.clear();
    }
    
    public void provisionTaskToAll(String name, Object... args){
        for(Entity e : entities){
            e.provisionTask(name, args);
        }
    }
    
    public void provisionTaskToTeam(int team, String name, Object... args){
        getTeamTasks(team).add(new Task(name, args));
    }
    
    public void refreshTeamTasks(){
        for(Entity e : entities){
            if(!(e instanceof Unit)) continue;
            Unit u = (Unit) e;
            if(u.isIdle()){
                Array<Task> tasks = getTeamTasks(u.getTeam().id);
                for(int i = 0; i < tasks.size; i++){
                    Task t = tasks.get(i);
                    u.provisionTask(t.getName(), t.getArgs());
                }
            }
        }
    }
    
    public Array<Task> getTeamTasks(int team){
        switch(team){
            case A_ID: return TASKS_A;
            case B_ID: return TASKS_B;
            case C_ID: return TASKS_C;
        }
        
        return null;
    }
}
