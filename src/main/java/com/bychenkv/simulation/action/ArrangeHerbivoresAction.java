package com.bychenkv.simulation.action;

import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.creature.Herbivore;
import com.bychenkv.simulation.utils.BfsResourceFinder;
import com.bychenkv.simulation.utils.ResourceFinder;

public class ArrangeHerbivoresAction extends ArrangeEntitiesAction {
    private static final int DEFAULT_HERBIVORE_HP = 2;
    private static final int DEFAULT_HERBIVORE_SPEED = 2;
    private static final int DEFAULT_HP_RESTORE_RATE = 1;

    public ArrangeHerbivoresAction(SimulationMap simulationMap, int herbivoresNumber) {
        super(simulationMap, herbivoresNumber);
    }

    @Override
    public Entity createEntity() {
        ResourceFinder resourceFinder = new BfsResourceFinder(map);
        return new Herbivore(DEFAULT_HERBIVORE_HP, DEFAULT_HERBIVORE_SPEED, DEFAULT_HP_RESTORE_RATE, resourceFinder);
    }
}
