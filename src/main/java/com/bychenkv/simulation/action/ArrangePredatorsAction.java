package com.bychenkv.simulation.action;

import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.creature.Predator;
import com.bychenkv.simulation.utils.BfsResourceFinder;
import com.bychenkv.simulation.utils.ResourceFinder;

public class ArrangePredatorsAction extends ArrangeEntitiesAction {
    private static final int DEFAULT_PREDATOR_HP = 2;
    private static final int DEFAULT_PREDATOR_SPEED = 3;
    private static final int DEFAULT_PREDATOR_ATTACK = 1;

    public ArrangePredatorsAction(SimulationMap simulationMap, int predatorsNumber) {
        super(simulationMap, predatorsNumber);
    }

    @Override
    public Entity createEntity() {
        ResourceFinder resourceFinder = new BfsResourceFinder(map);
        return new Predator(DEFAULT_PREDATOR_HP, DEFAULT_PREDATOR_SPEED, DEFAULT_PREDATOR_ATTACK, resourceFinder);
    }
}
