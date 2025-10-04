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
        BfsResourceFinderContext context = new BfsResourceFinderContext(start);

        while (context.hasNextPosition()) {
            Position current = context.pollNextPosition();

            for (Position neighbor : getUnvisitedNeighbors(current, context)) {
                Optional<Path> path = processNeighbor(neighbor, current, context, stopCondition);
                if (path.isPresent()) {
                    return path.get();
                }
            }
        }

        return Path.empty();
    }

    private List<Position> getUnvisitedNeighbors(Position current, BfsResourceFinderContext context) {
        return current.getNeighborsWithinBounds(map.getHeight(), map.getWidth())
                .stream()
                .filter(neighbor -> !context.isVisited(neighbor))
                .toList();
    }

    private Optional<Path> processNeighbor(Position neighbor,
                                       Position parent,
                                       BfsResourceFinderContext context,
                                       Predicate<Entity> stopCondition) {
        Entity entity = map.getEntityAt(neighbor);

        if (!isTraversable(entity, stopCondition)) {
            return Optional.empty();
        }

        context.addTransition(neighbor, parent);

        if (stopCondition.test(entity)) {
            return Optional.of(context.reconstructPath(neighbor));
        }

        context.enqueuePosition(neighbor);
        return Optional.empty();
    }

    private boolean isTraversable(Entity entity, Predicate<Entity> stopCondition) {
        return entity == null || stopCondition.test(entity);
    }
}