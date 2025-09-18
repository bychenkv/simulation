package com.bychenkv.simulation.action;

import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.object.Grass;

public class ArrangeGrassAction extends ArrangeEntitiesAction {
    public ArrangeGrassAction(SimulationMap simulationMap, int grassNumber) {
        super(simulationMap, grassNumber);
    }

    @Override
    public Entity createEntity() {
        return new Grass();
    }
}
