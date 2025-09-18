package com.bychenkv.simulation.action;

import com.bychenkv.simulation.core.SimulationMap;

public abstract class Action {
    protected final SimulationMap map;

    public Action(SimulationMap map) {
        this.map = map;
    }

    public abstract void execute();
}
