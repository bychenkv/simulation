package com.bychenkv.simulation.utils;

import com.bychenkv.simulation.core.Path;
import com.bychenkv.simulation.core.Position;
import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.Entity;

import java.util.*;
import java.util.function.Predicate;

public class BfsResourceFinder implements ResourceFinder {
    private final SimulationMap map;

    public BfsResourceFinder(SimulationMap map) {
        this.map = map;
    }

    public Path findPath(Position start, Predicate<Entity> stopCondition) {
        Map<Position, Position> transitions = new HashMap<>();
        Set<Position> visited = new HashSet<>();
        Queue<Position> queue = new LinkedList<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            Entity entity = map.getEntityAt(current);

            if (stopCondition.test(entity)) {
                return reconstructPath(transitions, start, current);
            }

            for (Position neighbor : current.getNeighbors()) {
                if (neighbor.withinMap(map) && !visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    transitions.put(neighbor, current);
                }
            }
        }

        return new Path();
    }

    private static Path reconstructPath(Map<Position, Position> transitions,
                                        Position start,
                                        Position end) {
        Path path = new Path();
        Position current = end;
        while (current != start) {
            path.add(current);
            current = transitions.get(current);
        }
        path.reverse();
        return path;
    }
}