package com.bychenkv.simulation.core;

import java.util.Arrays;
import java.util.List;

public record Position(int x, int y) {
    private static final Direction[] DIRECTIONS = Direction.values();

    public Position add(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    public List<Position> getNeighbors() {
        return Arrays.stream(DIRECTIONS)
                .map(this::offset)
                .toList();
    }

    private Position offset(Direction direction) {
        return direction.applyTo(this);
    }

    @Override
    public String toString() {
        return "(%d, %d)".formatted(x, y);
    }
}
