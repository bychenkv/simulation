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

    public boolean isOccupied(Position position) {
        return entityPositions.containsKey(position);
    }

    public Entity getEntityAt(Position position) {
        return entityPositions.get(position);
    }

    public Entity getEntityAt(int x, int y) {
        return entityPositions.get(new Position(x, y));
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
