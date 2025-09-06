package com.bychenkv.simulation.action;

import com.bychenkv.simulation.Map;

public abstract class Action {
    protected final Map map;

    public Action(Map map) {
        this.map = map;
    }

    public abstract void execute();
}
