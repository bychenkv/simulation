package com.bychenkv.simulation.core;

import com.bychenkv.simulation.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class SimulationMap {
    private final int height;
    private final int width;

    private final Map<Position, Entity> positionToEntity = new HashMap<>();
    private final Map<Entity, Position> entityToPosition = new HashMap<>();

    public SimulationMap(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public boolean isOccupied(Position position) {
        return positionToEntity.containsKey(position);
    }

    public Entity getEntityAt(Position position) {
        return positionToEntity.get(position);
    }

    public Entity getEntityAt(int x, int y) {
        Position position = new Position(x, y);
        return positionToEntity.get(position);
    }

    public Position getEntityPosition(Entity entity) {
        return entityToPosition.get(entity);
    }

    public void addEntity(Position position, Entity entity) {
        Position oldPosition = entityToPosition.get(entity);
        if (oldPosition != null) {
            positionToEntity.remove(oldPosition);
        }

        positionToEntity.put(position, entity);
        entityToPosition.put(entity, position);
    }

    public void removeEntityAt(Position position) {
        Entity entity = positionToEntity.remove(position);
        if (entity != null) {
            entityToPosition.remove(entity);
        }
    }

    public Map<Position, Entity> getEntityPositions() {
        return new HashMap<>(positionToEntity);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
