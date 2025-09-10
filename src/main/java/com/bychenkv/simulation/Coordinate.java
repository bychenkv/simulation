package com.bychenkv.simulation;

import java.util.List;

public record Coordinate(int x, int y) {
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinate(int thatX, int thatY))) {
            return false;
        }
        return x == thatX && y == thatY;
    }

    public boolean withinMap(Map map) {
        return x < map.getRows() && x >= 0 &&
               y < map.getColumns() && y >= 0;
    }

    public List<Coordinate> getNeighbors() {
        return List.of(new Coordinate(x + 1, y),
                new Coordinate(x, y + 1),
                new Coordinate(x - 1, y),
                new Coordinate(x, y - 1));
    }
}
