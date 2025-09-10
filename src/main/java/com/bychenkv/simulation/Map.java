package com.bychenkv.simulation;

import com.bychenkv.simulation.entity.Entity;

import java.util.HashMap;

public class Map {
    private final int rows;
    private final int columns;

    private final java.util.Map<Coordinate, Entity> entities;

    public Map(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        entities = new HashMap<>();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Entity getEntity(Coordinate coordinate) {
        return entities.get(coordinate);
    }

    public java.util.Map<Coordinate, Entity> getEntities() {
        return new HashMap<>(entities);
    }

    public void addEntity(Coordinate coordinate, Entity entity) {
        entities.put(coordinate, entity);
    }

    public void removeEntity(Coordinate coordinate) {
        entities.remove(coordinate);
    }
}
