package com.bychenkv.simulation.action;

import com.bychenkv.simulation.core.Position;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.core.SimulationMap;

import java.util.Random;

public abstract class ArrangeEntitiesAction extends Action {
    private final int entitiesNumber;
    private final Random random;

    public ArrangeEntitiesAction(SimulationMap simulationMap, int entitiesNumber) {
        super(simulationMap);
        this.entitiesNumber = entitiesNumber;
        random = new Random();
    }

    @Override
    public void execute() {
        for (int i = 0; i < entitiesNumber; i++) {
            Position position;
            do {
                position = new Position(
                        random.nextInt(map.getHeight()),
                        random.nextInt(map.getWidth())
                );
            } while (map.getEntityAt(position) != null);

            map.addEntity(position, createEntity());
        }
    }

    public abstract Entity createEntity();
}
