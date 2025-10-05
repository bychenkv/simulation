package com.bychenkv.simulation.action;

import com.bychenkv.simulation.core.Position;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.core.SimulationMap;

import java.util.Random;
import java.util.function.Supplier;

public class PopulateEntities<T extends Entity> extends Action {
    private final Class<T> entityType;
    private final int targetCount;
    private final Supplier<T> entityFactory;
    private final Random random = new Random();

    public PopulateEntities(Class<T> entityType, int targetCount, Supplier<T> entityFactory) {
        this.entityType = entityType;
        this.targetCount = targetCount;
        this.entityFactory = entityFactory;
    }

    @Override
    public void execute(SimulationMap map) {
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
