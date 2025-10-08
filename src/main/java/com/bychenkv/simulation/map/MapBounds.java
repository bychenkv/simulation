package com.bychenkv.simulation.map;

import java.util.Random;

public record MapBounds(int height, int width) {
    private static final int DEFAULT_HEIGHT = 10;
    private static final int DEFAULT_WIDTH = 10;

    public boolean contains(Position position) {
        return position.x() >= 0 && position.x() < height
               && position.y() >= 0 && position.y() < width;
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
