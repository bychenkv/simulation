package com.bychenkv.simulation.services.rendering;

import com.bychenkv.simulation.map.SimulationMap;
import org.jline.terminal.Terminal;

public class ConsoleMapRendererFactory implements MapRendererFactory {
    private final Terminal terminal;

    public ConsoleMapRendererFactory(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public MapRenderer createMapRenderer(SimulationMap map) {
        EntityRenderer entityRenderer = new ConsoleEntityRenderer();

        return new ConsoleMapRenderer(map, entityRenderer);
    }
}
