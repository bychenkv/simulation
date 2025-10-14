package com.bychenkv.simulation.core;

import com.bychenkv.simulation.action.Action;
import com.bychenkv.simulation.action.MoveCreatures;
import com.bychenkv.simulation.action.SpawnEntities;
import com.bychenkv.simulation.config.CreaturesConfig;
import com.bychenkv.simulation.config.EntitySpawnConfig;
import com.bychenkv.simulation.config.SimulationConfig;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.creature.*;
import com.bychenkv.simulation.entity.creature.factory.HerbivoreFactory;
import com.bychenkv.simulation.entity.creature.factory.PredatorFactory;
import com.bychenkv.simulation.entity.object.Grass;
import com.bychenkv.simulation.entity.object.Rock;
import com.bychenkv.simulation.entity.object.Tree;
import com.bychenkv.simulation.map.SimulationMap;
import com.bychenkv.simulation.services.finder.BfsResourceFinder;
import com.bychenkv.simulation.services.finder.ResourceFinder;
import com.bychenkv.simulation.services.rendering.MapRenderer;
import com.bychenkv.simulation.services.rendering.MapRendererFactory;
import com.bychenkv.simulation.ui.SimulationUi;

import java.util.List;
import java.util.function.Supplier;

public class DefaultSimulationFactory implements SimulationFactory {
    private final SimulationConfig config;
    private final MapRendererFactory rendererFactory;
    private final SimulationUi ui;

    public DefaultSimulationFactory(SimulationConfig config,
                                    MapRendererFactory rendererFactory,
                                    SimulationUi ui) {
        this.config = config;
        this.rendererFactory = rendererFactory;
        this.ui = ui;
    }

    @Override
    public Simulation createSimulation() {
        SimulationMap map = new SimulationMap(config.mapBounds());
        MapRenderer mapRenderer = rendererFactory.createMapRenderer(map);

        ResourceFinder resourceFinder = new BfsResourceFinder(map);
        SimulationActions actions = createActions(resourceFinder);

        return new Simulation(map, mapRenderer, actions, ui);
    }

    private SimulationActions createActions(ResourceFinder resourceFinder) {
        EntitySpawnConfig spawn = config.spawn();
        CreaturesConfig creatures = config.creatures();

        HerbivoreFactory herbivoreFactory = new HerbivoreFactory(
                creatures.herbivore(),
                resourceFinder
        );
        PredatorFactory predatorFactory = new PredatorFactory(
                creatures.predator(),
                resourceFinder
        );

        List<Action> initActions = List.of(
                spawnEntities(Rock.class, spawn.rocks(), Rock::new),
                spawnEntities(Tree.class, spawn.trees(), Tree::new),
                spawnEntities(Grass.class, spawn.grass(), Grass::new),
                spawnEntities(Herbivore.class, spawn.herbivores(), herbivoreFactory::create),
                spawnEntities(Predator.class, spawn.predators(), predatorFactory::create)
        );

        List<Action> turnActions = List.of(
                new MoveCreatures(),
                spawnEntities(Grass.class, spawn.grass(), Grass::new),
                spawnEntities(Herbivore.class, spawn.herbivores(), herbivoreFactory::create)
        );

        return new SimulationActions(initActions, turnActions);
    }

    private <T extends Entity> Action spawnEntities(
            Class<T> type,
            int count,
            Supplier<T> factory
    ) {
        return new SpawnEntities<>(type, count, factory);
    }
}
