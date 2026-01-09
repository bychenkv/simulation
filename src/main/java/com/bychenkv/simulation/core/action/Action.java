package com.bychenkv.simulation.core.action;

import com.bychenkv.simulation.core.simulation.SimulationState;
import com.bychenkv.simulation.core.map.SimulationMap;

public abstract class Action {
    public abstract void execute(SimulationMap map, SimulationState state);
}
