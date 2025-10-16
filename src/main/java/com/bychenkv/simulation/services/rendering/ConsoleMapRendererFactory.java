package com.bychenkv.simulation.services.rendering;

import com.bychenkv.simulation.map.SimulationMap;
import org.jline.terminal.Terminal;

public class ConsoleMapRendererFactory implements MapRendererFactory {
    @Override
    public MapRenderer createMapRenderer(SimulationMap map) {
        EntityRenderer entityRenderer = new ConsoleEntityRenderer();

        return new ConsoleMapRenderer(map, entityRenderer);
    }
}
