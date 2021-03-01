package com.xemplarsoft.games.cross.rps.controller.ai;

import com.xemplarsoft.games.cross.rps.model.Entity;
import com.xemplarsoft.games.cross.rps.model.World;

public interface AI {
    public boolean isRegistered();
    public boolean isIdle();
    public void register(Entity e);
    public void update(float delta, World w);
    public void provisionTask(String name, Object... args);
}
