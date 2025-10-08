package com.bychenkv.simulation.map;

import com.bychenkv.simulation.entity.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationMap {
    private final MapBounds bounds;

    private final Map<Position, Entity> positionToEntity = new HashMap<>();
    private final Map<Entity, Position> entityToPosition = new HashMap<>();

    public SimulationMap(MapBounds bounds) {
        this.bounds = bounds;
    }

    public boolean isOccupied(Position position) {
        return positionToEntity.containsKey(position);
    }

    public Entity getEntityAt(Position position) {
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

    public <T extends Entity> List<T> getEntitiesOfType(Class<T> type) {
        return positionToEntity.values()
                .stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
    }

    public MapBounds getBounds() {
        return bounds;
    }
}
