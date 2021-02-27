package com.xemplarsoft.games.cross.rps.model;

import com.xemplarsoft.games.cross.rps.model.World;

public interface Controller {
    public void update(float delta, Entity e, World w);
}
