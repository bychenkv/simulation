package com.bychenkv.simulation.core;

import com.bychenkv.simulation.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class SimulationMap {
    private final int height;
    private final int width;

    private final Map<Position, Entity> entityPositions;

    public SimulationMap(int height, int width) {
        this.height = height;
        this.width = width;

        entityPositions = new HashMap<>();
    }

    public Entity getEntityAt(Position position) {
        return entityPositions.get(position);
    }

    public void addEntity(Position position, Entity entity) {
        entityPositions.put(position, entity);
    }

    public void removeEntity(Position position) {
        entityPositions.remove(position);
    }

    public Map<Position, Entity> getEntityPositions() {
        return new HashMap<>(entityPositions);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
