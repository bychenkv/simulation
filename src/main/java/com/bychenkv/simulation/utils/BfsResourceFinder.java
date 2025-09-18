package com.bychenkv.simulation.utils;

import com.bychenkv.simulation.core.Path;
import com.bychenkv.simulation.core.Position;
import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.Consumable;
import com.bychenkv.simulation.entity.creature.Creature;
import com.bychenkv.simulation.entity.Entity;

import java.util.*;

public class BfsResourceFinder implements ResourceFinder {
    private final SimulationMap simulationMap;

    private final Map<Position, Position> transitions;
    private final Set<Position> visited;
    private final Queue<Position> queue;

    public BfsResourceFinder(SimulationMap simulationMap) {
        this.simulationMap = simulationMap;

        transitions = new HashMap<>();
        visited = new HashSet<>();
        queue = new LinkedList<>();
    }

    public Path findPath(Position start) {
        Path path = new Path();
        Creature creature = (Creature) simulationMap.getEntityAt(start);

        reset();
        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            Entity entity = simulationMap.getEntityAt(current);

            if (consumableFound(creature, entity)) {
                path = reconstructPath(start, current);
                break;
            }

            for (Position neighbor : current.getNeighbors()) {
                if (isAvailable(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    transitions.put(neighbor, current);
                }
            }
        }

        return path;
    }

    private static boolean consumableFound(Creature consumer, Entity entity) {
        return entity instanceof Consumable consumable && consumable.canBeConsumedBy(consumer);
    }

    private boolean isAvailable(Position position) {
        return position.withinMap(simulationMap) && !visited.contains(position);
    }

    private Path reconstructPath(Position start, Position end) {
        Path path = new Path();
        Position current = end;

        while (current != start) {
            path.add(current);
            current = transitions.get(current);
        }

        path.reverse();
        return path;
    }

    private void reset() {
        transitions.clear();
        visited.clear();
        queue.clear();
    }
}
