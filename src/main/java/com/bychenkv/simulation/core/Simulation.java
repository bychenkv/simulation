package com.bychenkv.simulation.core;

import com.bychenkv.simulation.action.Action;
import com.bychenkv.simulation.rendering.MapRenderer;

import java.util.List;

public class Simulation {
    private int iterationCount;
    private final SimulationMap map;
    private final MapRenderer mapRenderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;

    public Simulation(SimulationMap map,
                      MapRenderer mapRenderer,
                      List<Action> initActions,
                      List<Action> turnActions) {
        this.map = map;
        this.mapRenderer = mapRenderer;
        this.initActions = initActions;
        this.turnActions = turnActions;
    }

    public void start() {
        System.out.println("Start simulation...");

        for (Action initAction : initActions) {
            initAction.execute(map);
        }

        while (iterationCount < 10) {
            System.out.println("Move counter: " + iterationCount);
            mapRenderer.render();

            for (Action turnAction : turnActions) {
                turnAction.execute(map);
            }
            iterationCount++;
        }
    }
}
