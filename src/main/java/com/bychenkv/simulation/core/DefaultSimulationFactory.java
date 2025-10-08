package com.bychenkv.simulation.core;

import com.bychenkv.simulation.action.Action;
import com.bychenkv.simulation.action.MoveCreatures;
import com.bychenkv.simulation.action.PopulateEntities;
import com.bychenkv.simulation.config.SimulationConfig;
import com.bychenkv.simulation.entity.creature.Herbivore;
import com.bychenkv.simulation.entity.creature.Predator;
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

        List<Action> initActions = List.of(
                createRocksPopulation(),
                createTreesPopulation(),
                createGrassPopulation(),
                createHerbivoresPopulation(resourceFinder),
                createPredatorsPopulation(resourceFinder)
        );

        List<Action> turnActions = List.of(
                new MoveCreatures(),
                createGrassPopulation(),
                createHerbivoresPopulation(resourceFinder)
        );

        return new Simulation.Builder()
                .withMap(map)
                .withRenderer(mapRenderer)
                .withInitActions(initActions)
                .withTurnActions(turnActions)
                .build();
    }

    private SimulationMap createMap() {
        MapBounds mapBounds = simulationConfig.mapBounds();
        return new SimulationMap(mapBounds);
    }

    private MapRenderer createMapRenderer(SimulationMap map) {
        EntityRenderer entityRenderer = new ConsoleEntityRenderer();
        return new ConsoleMapRenderer(map, System.out, entityRenderer);
    }

    private Action createRocksPopulation() {
        return new PopulateEntities<>(
                Rock.class,
                simulationConfig.populationConfig().getRocksNumber(),
                Rock::new
        );
    }

    private Action createTreesPopulation() {
        return new PopulateEntities<>(
                Tree.class,
                simulationConfig.populationConfig().getTreesNumber(),
                Tree::new
        );
    }

    private Action createGrassPopulation() {
        return new PopulateEntities<>(
                Grass.class,
                simulationConfig.populationConfig().getGrassNumber(),
                Grass::new
        );
    }

    private Action createHerbivoresPopulation(ResourceFinder resourceFinder) {
        return new PopulateEntities<>(
                Herbivore.class,
                simulationConfig.populationConfig().getHerbivoresNumber(),
                () -> new Herbivore(2, 2, 1, resourceFinder)
        );
    }

    private Action createPredatorsPopulation(ResourceFinder resourceFinder) {
        return new PopulateEntities<>(
                Predator.class,
                simulationConfig.populationConfig().getPredatorsNumber(),
                () -> new Predator(2, 3, 1, resourceFinder)
        );
    }
}
