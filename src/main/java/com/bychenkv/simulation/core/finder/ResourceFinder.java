package com.bychenkv.simulation.core.finder;

import com.bychenkv.simulation.core.map.Position;
import com.bychenkv.simulation.core.entity.Entity;

import java.util.function.Predicate;

public interface ResourceFinder {
    Path findPath(Position start, Predicate<Entity> stopCondition);
}
