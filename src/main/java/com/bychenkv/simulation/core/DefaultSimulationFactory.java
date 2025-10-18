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
import com.bychenkv.simulation.services.logger.SimulationLogger;
import com.bychenkv.simulation.map.SimulationMap;
import com.bychenkv.simulation.services.finder.BfsResourceFinder;
import com.bychenkv.simulation.services.finder.ResourceFinder;
import com.bychenkv.simulation.services.rendering.*;

import java.util.List;
import java.util.function.Supplier;

public class DefaultSimulationFactory implements SimulationFactory {
    private final SimulationConfig config;
    private final SimulationEventBus eventBus;
    private final SimulationLogger logger;

    public DefaultSimulationFactory(SimulationConfig config,
                                    SimulationEventBus eventBus,
                                    SimulationLogger logger) {
        this.config = config;
        this.eventBus = eventBus;
        this.logger = logger;
    }

    @Override
    public Simulation createSimulation() {
        SimulationMap map = new SimulationMap(config.mapBounds());

        EntityRenderer entityRenderer = new ConsoleEntityRenderer();
        MapRenderer mapRenderer = new ConsoleMapRenderer(map, entityRenderer);

        ResourceFinder resourceFinder = new BfsResourceFinder(map);
        SimulationActions actions = createActions(resourceFinder, mapRenderer);

        SimulationContext context = new SimulationContext(map);

        return new Simulation(mapRenderer, actions, eventBus, logger, context);
    }

    private SimulationActions createActions(ResourceFinder resourceFinder, MapRenderer mapRenderer) {
        EntitySpawnConfig spawn = config.spawn();
        CreaturesConfig creatures = config.creatures();

        HerbivoreFactory herbivoreFactory = new HerbivoreFactory(
                creatures.herbivore(),
                resourceFinder,
                logger
        );
        PredatorFactory predatorFactory = new PredatorFactory(
                creatures.predator(),
                resourceFinder,
                logger
        );

        List<Action> initActions = List.of(
                spawnEntities(Rock.class, spawn.rocks(), Rock::new),
                spawnEntities(Tree.class, spawn.trees(), Tree::new),
                spawnEntities(Grass.class, spawn.grass(), Grass::new),
                spawnEntities(Herbivore.class, spawn.herbivores(), herbivoreFactory::create),
                spawnEntities(Predator.class, spawn.predators(), predatorFactory::create)
        );

        List<Action> turnActions = List.of(
                new MoveCreatures(mapRenderer, eventBus),
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
