package com.bychenkv.simulation;

import com.bychenkv.simulation.action.*;
import com.bychenkv.simulation.utils.MapRenderer;

import java.util.List;

public class Simulation {
    private static final int DEFAULT_ROCKS_NUMBER = 5;
    private static final int DEFAULT_TREES_NUMBER = 5;
    private static final int DEFAULT_GRASS_NUMBER = 5;
    private static final int DEFAULT_HERBIVORES_NUMBER = 5;
    private static final int DEFAULT_PREDATORS_NUMBER = 5;


    private final MapRenderer mapRenderer;
    private int moveCounter;

    private final List<Action> initActions;
    private final List<Action> turnActions;

    public Simulation(int rows, int columns) {
        Map map = new Map(rows, columns);
        mapRenderer = new MapRenderer(map);

        initActions = List.of(new ArrangeRocksAction(map, DEFAULT_ROCKS_NUMBER),
                new ArrangeTreesAction(map, DEFAULT_TREES_NUMBER),
                new ArrangeGrassAction(map, DEFAULT_GRASS_NUMBER),
                new ArrangeHerbivoresAction(map, DEFAULT_HERBIVORES_NUMBER),
                new ArrangePredatorsAction(map, DEFAULT_PREDATORS_NUMBER));

        turnActions = List.of(new MoveCreaturesAction(map));
    }

    public void start() {
        System.out.println("Start simulation...");

        for (Action initAction : initActions) {
            initAction.execute();
        }

        while (moveCounter < 2) {
            mapRenderer.render();
            moveCounter++;
            System.out.println("Move counter: " + moveCounter);

            for (Action turnAction : turnActions) {
                turnAction.execute();
            }
        }
    }
}
