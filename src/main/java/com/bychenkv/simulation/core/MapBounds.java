package com.bychenkv.simulation.core;

import java.util.Random;

public record MapBounds(int height, int width) {
    public boolean contains(Position position) {
        return position.x() >= 0 && position.x() < height
               && position.y() >= 0 && position.y() < width;
    }

    public Position randomPosition(Random random) {
        int x = random.nextInt(height);
        int y = random.nextInt(width);
        return new Position(x, y);
    }
}
