package com.bychenkv.simulation.core;

import java.util.Arrays;
import java.util.List;

public record Position(int x, int y) {
    private static final Direction[] DIRECTIONS = Direction.values();

    public Position add(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    public List<Position> getNeighborsWithinBounds(int height, int width) {
        return Arrays.stream(DIRECTIONS)
                .map(this::move)
                .filter(p -> p.isWithin(height, width))
                .toList();
    }

    private Position move(Direction direction) {
        return direction.applyTo(this);
    }

    @Override
    public String toString() {
        return "(%d, %d)".formatted(x, y);
    }

    private boolean isWithin(int height, int width) {
        return x < height && x >= 0 && y < width && y >= 0;
    }
}
