package com.bychenkv.simulation.core;

import com.bychenkv.simulation.action.Action;
import com.bychenkv.simulation.action.ArrangeEntities;
import com.bychenkv.simulation.action.MoveCreatures;
import com.bychenkv.simulation.entity.creature.Herbivore;
import com.bychenkv.simulation.entity.creature.Predator;
import com.bychenkv.simulation.entity.object.Grass;
import com.bychenkv.simulation.entity.object.Rock;
import com.bychenkv.simulation.entity.object.Tree;
import com.bychenkv.simulation.rendering.MapRenderer;
import com.bychenkv.simulation.utils.ResourceFinder;

import java.util.List;

public class Simulation {
    private static final int DEFAULT_ROCKS_NUMBER = 5;
    private static final int DEFAULT_TREES_NUMBER = 5;
    private static final int DEFAULT_GRASS_NUMBER = 5;
    private static final int DEFAULT_HERBIVORES_NUMBER = 5;
    private static final int DEFAULT_PREDATORS_NUMBER = 5;

    private int moveCounter;
    private final SimulationMap map;
    private final MapRenderer mapRenderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;

    public Simulation(SimulationMap map, MapRenderer mapRenderer, ResourceFinder resourceFinder) {
        this.map = map;
        this.mapRenderer = mapRenderer;

        initActions = List.of(
                new ArrangeEntities(DEFAULT_ROCKS_NUMBER, Rock::new),
                new ArrangeEntities(DEFAULT_TREES_NUMBER, Tree::new),
                new ArrangeEntities(DEFAULT_GRASS_NUMBER, Grass::new),
                new ArrangeEntities(
                    DEFAULT_HERBIVORES_NUMBER,
                    () -> new Herbivore(2, 2, 1, resourceFinder)
                ),
                new ArrangeEntities(
                    DEFAULT_PREDATORS_NUMBER,
                    () -> new Predator(2, 3, 1, resourceFinder)
                )
        );
        turnActions = List.of(new MoveCreatures());
    }

    public void start() {
        System.out.println("Start simulation...");

        for (Action initAction : initActions) {
            initAction.execute(map);
        }

        while (moveCounter < 10) {
            mapRenderer.render();
            moveCounter++;
            System.out.println("Move counter: " + moveCounter);

            for (Action turnAction : turnActions) {
                turnAction.execute(map);
            }
        }
    }
}
