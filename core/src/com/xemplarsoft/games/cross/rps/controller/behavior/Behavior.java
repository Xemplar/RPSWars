package com.xemplarsoft.games.cross.rps.controller.behavior;

import com.xemplarsoft.games.cross.rps.model.Entity;
import com.xemplarsoft.games.cross.rps.model.World;

public interface Behavior {
    public boolean execute(Entity e, float delta, World w, Object... args);
}
