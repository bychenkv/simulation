package com.bychenkv.simulation.rendering;

import com.bychenkv.simulation.map.SimulationMap;
import org.jline.terminal.Terminal;

import java.io.PrintWriter;

public class ConsoleMapRendererFactory implements MapRendererFactory {
    private final Terminal terminal;

    public ConsoleMapRendererFactory(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public MapRenderer createMapRenderer(SimulationMap map) {
        PrintWriter writer = terminal.writer();
        EntityRenderer entityRenderer = new ConsoleEntityRenderer();

        return new ConsoleMapRenderer(map, writer, entityRenderer);
    }
}
