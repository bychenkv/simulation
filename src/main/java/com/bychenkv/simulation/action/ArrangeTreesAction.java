package com.bychenkv.simulation.action;

import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.object.Tree;

public class ArrangeTreesAction extends ArrangeEntitiesAction {
    public ArrangeTreesAction(SimulationMap simulationMap, int treesNumber) {
        super(simulationMap, treesNumber);
    }

    @Override
    public Entity createEntity() {
        return new Tree();
    }
}
