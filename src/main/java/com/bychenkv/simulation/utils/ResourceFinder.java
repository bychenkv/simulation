package com.bychenkv.simulation.utils;

import com.bychenkv.simulation.core.Path;
import com.bychenkv.simulation.core.Position;

public interface ResourceFinder {
    Path findPath(Position start);
}
