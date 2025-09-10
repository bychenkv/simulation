package com.bychenkv.simulation.utils;

import com.bychenkv.simulation.Coordinate;
import com.bychenkv.simulation.Map;
import com.bychenkv.simulation.entity.Creature;
import com.bychenkv.simulation.entity.Entity;

import java.util.*;

public class ResourceFinder {
    private final Map map;

    private final java.util.Map<Coordinate, Coordinate> transitions;
    private final Set<Coordinate> visited;
    private final Queue<Coordinate> queue;

    public ResourceFinder(Map map) {
        this.map = map;

        transitions = new HashMap<>();
        visited = new HashSet<>();
        queue = new LinkedList<>();
    }

    public List<Coordinate> findPath(Coordinate start) {
        reset();

        Creature creature = (Creature) map.getEntity(start);
        queue.offer(start);
        visited.add(start);
        transitions.put(start, null);

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            Entity entity = map.getEntity(current);

            if (creature.canConsume(entity)) {
                return buildPath(current);
            }

            for (Coordinate neighbor : current.getNeighbors()) {
                if (neighbor.withinMap(map) && !visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    transitions.put(neighbor, current);
                }
            }
        }

        return List.of();
    }

    private void reset() {
        transitions.clear();
        visited.clear();
        queue.clear();
    }

    private List<Coordinate> buildPath(Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate current = end;

        while (current != null) {
            path.add(current);
            current = transitions.get(current);
        }

        Collections.reverse(path);
        return path;
    }
}
