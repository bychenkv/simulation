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
import com.bychenkv.simulation.map.MapBounds;
import com.bychenkv.simulation.map.SimulationMap;
import com.bychenkv.simulation.rendering.ConsoleEntityRenderer;
import com.bychenkv.simulation.rendering.ConsoleMapRenderer;
import com.bychenkv.simulation.rendering.EntityRenderer;
import com.bychenkv.simulation.rendering.MapRenderer;
import com.bychenkv.simulation.utils.BfsResourceFinder;
import com.bychenkv.simulation.utils.ResourceFinder;

import java.util.List;
import java.util.function.Supplier;

public class DefaultSimulationFactory implements SimulationFactory {
    private final SimulationConfig simulationConfig;

    public DefaultSimulationFactory(SimulationConfig simulationConfig) {
        this.simulationConfig = simulationConfig;
    }

    @Override
    public Simulation createSimulation() {
        SimulationMap map = createMap();
        MapRenderer mapRenderer = createMapRenderer(map);

        ResourceFinder resourceFinder = new BfsResourceFinder(map);
        SimulationActions actions = createActions(resourceFinder);

        return new Simulation(map, mapRenderer, actions);
    }

    private SimulationMap createMap() {
        MapBounds mapBounds = simulationConfig.mapBounds();
        return new SimulationMap(mapBounds);
    }

    private MapRenderer createMapRenderer(SimulationMap map) {
        EntityRenderer entityRenderer = new ConsoleEntityRenderer();
        return new ConsoleMapRenderer(map, System.out, entityRenderer);
    }

    private SimulationActions createActions(ResourceFinder resourceFinder) {
        EntitySpawnConfig spawn = simulationConfig.spawn();
        CreaturesConfig creaturesConfig = simulationConfig.creatures();

        HerbivoreFactory herbivoreFactory = new HerbivoreFactory(
                creaturesConfig.herbivore(),
                resourceFinder
        );
        PredatorFactory predatorFactory = new PredatorFactory(
                creaturesConfig.predator(),
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
