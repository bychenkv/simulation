package com.bychenkv.simulation.utils;

import com.bychenkv.simulation.map.Position;
import com.bychenkv.simulation.entity.Entity;

import java.util.function.Predicate;

public interface ResourceFinder {
    Path findPath(Position start, Predicate<Entity> stopCondition);
}
