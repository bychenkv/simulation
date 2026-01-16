package com.bychenkv.simulation.core.action;

import com.bychenkv.simulation.config.CreaturesConfig;
import com.bychenkv.simulation.config.EntitySpawnConfig;
import com.bychenkv.simulation.config.SimulationConfig;
import com.bychenkv.simulation.core.finder.BfsResourceFinder;
import com.bychenkv.simulation.core.simulation.SimulationEventBus;
import com.bychenkv.simulation.core.entity.Entity;
import com.bychenkv.simulation.core.entity.creature.Herbivore;
import com.bychenkv.simulation.core.entity.creature.Predator;
import com.bychenkv.simulation.core.entity.creature.factory.HerbivoreFactory;
import com.bychenkv.simulation.core.entity.creature.factory.PredatorFactory;
import com.bychenkv.simulation.core.entity.object.Grass;
import com.bychenkv.simulation.core.entity.object.Rock;
import com.bychenkv.simulation.core.entity.object.Tree;
import com.bychenkv.simulation.logger.SimulationLogger;

import java.util.List;
import java.util.function.Supplier;

public class SimulationActionsFactory {
    private final SimulationConfig simulationConfig;
    private final SimulationLogger logger;
    private final SimulationEventBus eventBus;
    private final BfsResourceFinder resourceFinder;

    public SimulationActionsFactory(SimulationConfig simulationConfig,
                                    SimulationLogger logger,
                                    SimulationEventBus eventBus,
                                    BfsResourceFinder resourceFinder) {
        this.simulationConfig = simulationConfig;
        this.logger = logger;
        this.eventBus = eventBus;
        this.resourceFinder = resourceFinder;
    }

    public SimulationActions createActions() {
        EntitySpawnConfig spawnConfig = simulationConfig.spawn();
        CreaturesConfig creaturesConfig = simulationConfig.creatures();

        HerbivoreFactory herbivoreFactory = new HerbivoreFactory(
                creaturesConfig.herbivore(),
                resourceFinder,
                logger
        );
        PredatorFactory predatorFactory = new PredatorFactory(
                creaturesConfig.predator(),
                resourceFinder,
                logger
        );

        List<Action> initActions = getInitActions(spawnConfig, herbivoreFactory, predatorFactory);
        List<Action> turnActions = getTurnActions(spawnConfig, herbivoreFactory);

        return new SimulationActions(initActions, turnActions);
    }

    private List<Action> getInitActions(EntitySpawnConfig spawnConfig,
                                        HerbivoreFactory herbivoreFactory,
                                        PredatorFactory predatorFactory) {
        return List.of(
                spawnEntities(Rock.class, spawnConfig.rocks(), Rock::new),
                spawnEntities(Tree.class, spawnConfig.trees(), Tree::new),
                spawnEntities(Grass.class, spawnConfig.grass(), Grass::new),
                spawnEntities(Herbivore.class, spawnConfig.herbivores(), herbivoreFactory::create),
                spawnEntities(Predator.class, spawnConfig.predators(), predatorFactory::create)
        );
    }

    private List<Action> getTurnActions(EntitySpawnConfig spawnConfig,
                                        HerbivoreFactory herbivoreFactory) {
        return List.of(
                new MoveCreatures(eventBus),
                spawnEntities(Grass.class, spawnConfig.grass(), Grass::new),
                spawnEntities(Herbivore.class, spawnConfig.herbivores(), herbivoreFactory::create)
        );
    }

    private <T extends Entity> Action spawnEntities(
            Class<T> type,
            int count,
            Supplier<T> factory
    ) {
        return new SpawnEntities<>(type, count, factory);
    }
}
