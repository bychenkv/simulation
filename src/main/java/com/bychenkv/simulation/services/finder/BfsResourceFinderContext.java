package com.bychenkv.simulation.services.finder;

import com.bychenkv.simulation.map.Position;

import java.util.*;

public class BfsResourceFinderContext {
    private final Map<Position, Position> transitions;
    private final Set<Position> visited;
    private final Queue<Position> queue;

    private final Position start;

    BfsResourceFinderContext(Position start) {
        transitions = new HashMap<>();
        visited = new HashSet<>();
        queue = new LinkedList<>();

        this.start = start;

        queue.offer(start);
        visited.add(start);
    }

    public boolean hasNextPosition() {
        return !queue.isEmpty();
    }

    public Position pollNextPosition() {
        return queue.poll();
    }

    public boolean isVisited(Position position) {
        return visited.contains(position);
    }

    public void addTransition(Position current, Position parent) {
        transitions.put(current, parent);
    }

    public void enqueuePosition(Position position) {
        queue.offer(position);
        visited.add(position);
    }

    public Path reconstructPath(Position end) {
        Path path = Path.empty();
        Position current = end;

        while (current != start) {
            path.add(current);
            current = transitions.get(current);
        }

        path.reverse();
        return path;
    }
}
