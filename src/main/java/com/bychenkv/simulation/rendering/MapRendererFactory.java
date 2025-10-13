package com.bychenkv.simulation.rendering;

import com.bychenkv.simulation.map.SimulationMap;

public interface MapRendererFactory {
    MapRenderer createMapRenderer(SimulationMap map);
}
