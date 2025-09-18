package com.bychenkv.simulation.action;

import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.object.Rock;

public class ArrangeRocksAction extends ArrangeEntitiesAction {
    public ArrangeRocksAction(SimulationMap simulationMap, int rocksNumber) {
        super(simulationMap, rocksNumber);
    }

    @Override
    public Entity createEntity() {
        return new Rock();
    }
}
