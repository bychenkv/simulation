package com.bychenkv.simulation.action;

import com.bychenkv.simulation.core.Position;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.core.SimulationMap;

import java.util.Random;
import java.util.function.Supplier;

public class ArrangeEntities extends Action {
    private final int entitiesNumber;
    private final Supplier<? extends  Entity> entityFactory;
    private final Random random = new Random();

    public ArrangeEntities(int entitiesNumber, Supplier<? extends  Entity> entityFactory) {
        this.entitiesNumber = entitiesNumber;
        this.entityFactory = entityFactory;
    }

    @Override
    public void execute(SimulationMap map) {
        for (int i = 0; i < entitiesNumber; i++) {
            Position position = generateFreePosition(map);
            Entity entity = entityFactory.get();
            map.addEntity(position, entity);
        }
    }

    private Position generateFreePosition(SimulationMap map) {
        Position position;
        do {
            int x = random.nextInt(map.getHeight());
            int y = random.nextInt(map.getWidth());
            position = new Position(x, y);
        } while (map.isOccupied(position));

        return position;
    }
}
