package com.bychenkv.simulation.core.map;

import java.util.Random;

public record MapBounds(int height, int width) {
    private static final int DEFAULT_HEIGHT = 20;
    private static final int DEFAULT_WIDTH = 20;

    public boolean contains(Position position) {
        int x = position.x();
        int y = position.y();

        return x >= 0 && x < height && y >= 0 && y < width;
    }

    public Position randomPosition(Random random) {
        int x = random.nextInt(height);
        int y = random.nextInt(width);
        return new Position(x, y);
    }

    public static MapBounds withDefaults() {
        return new MapBounds(DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }
}
