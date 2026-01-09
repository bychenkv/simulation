package com.bychenkv.simulation.core.action;

import com.bychenkv.simulation.core.simulation.SimulationState;
import com.bychenkv.simulation.core.map.Position;
import com.bychenkv.simulation.core.entity.Entity;
import com.bychenkv.simulation.core.map.SimulationMap;

import java.util.Random;
import java.util.function.Supplier;

public class SpawnEntities<T extends Entity> extends Action {
    private final Class<T> entityType;
    private final int targetCount;
    private final Supplier<T> entityFactory;
    private final Random random;

    public SpawnEntities(Class<T> entityType, int targetCount, Supplier<T> entityFactory) {
        this.entityType = entityType;
        this.targetCount = targetCount;
        this.entityFactory = entityFactory;
        random = new Random();
    }

    @Override
    public void execute(SimulationMap map, SimulationState state) {
        int existingCount = map.getEntitiesOfType(entityType).size();
        int missingCount = targetCount - existingCount;

        for (int i = 0; i < missingCount; i++) {
            Position freePosition = findFreePosition(map);
            Entity entity = entityFactory.get();
            map.addEntity(freePosition, entity);
        }
    }

    private Position findFreePosition(SimulationMap map) {
        Position position;
        do {
            position = map.getBounds().randomPosition(random);
        } while (map.isOccupied(position));
        return position;
    }
}
