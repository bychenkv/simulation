package com.bychenkv.simulation;

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

    public Entity getEntity(int x, int y) {
        return entities.get(new Coordinate(x, y));
    }

    public java.util.Map<Coordinate, Entity> getEntities() {
        return new HashMap<>(entities);
    }
}
