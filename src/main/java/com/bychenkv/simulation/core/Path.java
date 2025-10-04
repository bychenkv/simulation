package com.bychenkv.simulation.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {
    private final List<Position> positions;

    public Path() {
        positions = new ArrayList<>();
    }

    public static Path empty() {
        return new Path();
    }

    public int getLength() {
        return positions.size();
    }

    public void add(Position position) {
        positions.add(position);
    }

    public void reverse() {
        Collections.reverse(positions);
    }

    public Position getResourcePosition() {
        return positions.getLast();
    }

    public Position getNextPosition(int stepSize) {
        int nextPositionPathIndex = Math.min(positions.size() - 1, stepSize) - 1;
        return positions.get(nextPositionPathIndex);
    }
}
