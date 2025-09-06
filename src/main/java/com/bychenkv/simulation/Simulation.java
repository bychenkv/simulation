package com.bychenkv.simulation;

import com.bychenkv.simulation.action.Action;
import com.bychenkv.simulation.action.ArrangeRocksAction;

import java.util.List;

public class Simulation {
    private static final int DEFAULT_ROCKS_NUMBER = 5;

    private final MapRenderer mapRenderer;
    private int moveCounter;

    private final List<Action> initActions;
    private final List<Action> turnActions;

    public Simulation(int rows, int columns) {
        Map map = new Map(rows, columns);
        mapRenderer = new MapRenderer(map);

        initActions = List.of(new ArrangeRocksAction(map, DEFAULT_ROCKS_NUMBER));
        turnActions = List.of();
    }

    public void start() {
        System.out.println("Start simulation...");

        for (Action initAction : initActions) {
            initAction.execute();
        }

        while (moveCounter < 5) {
            mapRenderer.render();
            moveCounter++;
            System.out.println("Move counter: " + moveCounter);

            for (Action turnAction : turnActions) {
                turnAction.execute();
            }
        }
    }
}
