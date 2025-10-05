package com.bychenkv.simulation;

import com.bychenkv.simulation.action.Action;
import com.bychenkv.simulation.action.MoveCreatures;
import com.bychenkv.simulation.action.PopulateEntities;
import com.bychenkv.simulation.core.MapBounds;
import com.bychenkv.simulation.core.Simulation;
import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.creature.Herbivore;
import com.bychenkv.simulation.entity.creature.Predator;
import com.bychenkv.simulation.entity.object.Grass;
import com.bychenkv.simulation.entity.object.Rock;
import com.bychenkv.simulation.entity.object.Tree;
import com.bychenkv.simulation.rendering.ConsoleEntityRenderer;
import com.bychenkv.simulation.rendering.ConsoleMapRenderer;
import com.bychenkv.simulation.rendering.EntityRenderer;
import com.bychenkv.simulation.rendering.MapRenderer;
import com.bychenkv.simulation.utils.BfsResourceFinder;
import com.bychenkv.simulation.utils.ResourceFinder;

import java.util.List;

public class Launcher {
    private static final int DEFAULT_MAP_HEIGHT = 10;
    private static final int DEFAULT_MAP_WIDTH = 10;

    private static final int DEFAULT_ROCKS_NUMBER = 5;
    private static final int DEFAULT_TREES_NUMBER = 5;
    private static final int DEFAULT_GRASS_NUMBER = 5;
    private static final int DEFAULT_HERBIVORES_NUMBER = 5;
    private static final int DEFAULT_PREDATORS_NUMBER = 5;

    public static void main(String[] args) {
        Simulation simulation = setupSimulation();
        simulation.start();
    }

    private static Simulation setupSimulation() {
        MapBounds bounds = new MapBounds(DEFAULT_MAP_HEIGHT, DEFAULT_MAP_WIDTH);
        SimulationMap map = new SimulationMap(bounds);

        EntityRenderer entityRenderer = new ConsoleEntityRenderer();
        MapRenderer mapRenderer = new ConsoleMapRenderer(map, System.out, entityRenderer);
        ResourceFinder resourceFinder = new BfsResourceFinder(map);

        List<Action> initActions = List.of(
                new PopulateEntities<>(Rock.class, DEFAULT_ROCKS_NUMBER, Rock::new),
                new PopulateEntities<>(Tree.class, DEFAULT_TREES_NUMBER, Tree::new),
                new PopulateEntities<>(Grass.class, DEFAULT_GRASS_NUMBER, Grass::new),
                new PopulateEntities<>(
                        Herbivore.class,
                        DEFAULT_HERBIVORES_NUMBER,
                        () -> new Herbivore(2, 2, 1, resourceFinder)
                ),
                new PopulateEntities<>(
                        Predator.class,
                        DEFAULT_PREDATORS_NUMBER,
                        () -> new Predator(2, 3, 1, resourceFinder)
                )
        );

        List<Action> turnActions = List.of(
                new MoveCreatures(),
                new PopulateEntities<>(Grass.class, DEFAULT_GRASS_NUMBER, Grass::new),
                new PopulateEntities<>(
                        Herbivore.class,
                        DEFAULT_HERBIVORES_NUMBER,
                        () -> new Herbivore(2, 2, 1, resourceFinder)
                )
        );

        return new Simulation(map, mapRenderer, initActions, turnActions);
    }
}
