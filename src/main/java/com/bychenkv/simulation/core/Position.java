package com.bychenkv.simulation.core;

import java.util.List;

public record Position(int x, int y) {
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position(int thatX, int thatY))) {
            return false;
        }
        return x == thatX && y == thatY;
    }

    public boolean withinMap(SimulationMap simulationMap) {
        return x < simulationMap.getHeight() && x >= 0 &&
               y < simulationMap.getWidth() && y >= 0;
    }

    public List<Position> getNeighbors() {
        return List.of(new Position(x + 1, y),
                new Position(x, y + 1),
                new Position(x - 1, y),
                new Position(x, y - 1));
    }
}
