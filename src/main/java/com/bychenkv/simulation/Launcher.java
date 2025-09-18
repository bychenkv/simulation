package com.bychenkv.simulation;

import com.bychenkv.simulation.core.Simulation;
import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.utils.ConsoleMapRenderer;
import com.bychenkv.simulation.utils.MapRenderer;

public class Launcher {
    private static final int DEFAULT_MAP_HEIGHT = 10;
    private static final int DEFAULT_MAP_WIDTH = 10;

    public static void main(String[] args) {
        Simulation simulation = setupSimulation();
        simulation.start();
    }

    private static Simulation setupSimulation() {
        SimulationMap map = new SimulationMap(DEFAULT_MAP_HEIGHT, DEFAULT_MAP_WIDTH);
        MapRenderer mapRenderer = new ConsoleMapRenderer(map);

        return new Simulation(map, mapRenderer);
    }
}
