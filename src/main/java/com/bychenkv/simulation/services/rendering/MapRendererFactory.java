package com.bychenkv.simulation.services.rendering;

import com.bychenkv.simulation.map.SimulationMap;

public interface MapRendererFactory {
    MapRenderer createMapRenderer(SimulationMap map);
}
