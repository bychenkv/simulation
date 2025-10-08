package com.bychenkv.simulation.core;

import com.bychenkv.simulation.action.Action;
import com.bychenkv.simulation.map.SimulationMap;
import com.bychenkv.simulation.rendering.MapRenderer;

public class Simulation {
    private final SimulationMap map;
    private final MapRenderer mapRenderer;
    private final SimulationActions actions;

    private int iterationCount;

    public Simulation(
            SimulationMap map,
            MapRenderer mapRenderer,
            SimulationActions actions
    ) {
        this.map = map;
        this.mapRenderer = mapRenderer;
        this.actions = actions;
    }

    public void start() {
        System.out.println("Start simulation...");

        for (Action initAction : actions.init()) {
            initAction.execute(map);
        }

        while (iterationCount < 10) {
            System.out.println("Move counter: " + iterationCount);
            mapRenderer.render();

            for (Action turnAction : actions.turn()) {
                turnAction.execute(map);
            }
            iterationCount++;
        }
    }
}
