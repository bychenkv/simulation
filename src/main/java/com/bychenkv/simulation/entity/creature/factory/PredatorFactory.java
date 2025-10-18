package com.bychenkv.simulation.entity.creature.factory;

import com.bychenkv.simulation.config.PredatorConfig;
import com.bychenkv.simulation.entity.creature.Predator;
import com.bychenkv.simulation.services.finder.ResourceFinder;
import com.bychenkv.simulation.services.logger.SimulationLogger;

public class PredatorFactory extends CreatureFactory<Predator, PredatorConfig> {
    public PredatorFactory(PredatorConfig config,
                           ResourceFinder resourceFinder,
                           SimulationLogger logger) {
        super(config, resourceFinder, logger);
    }

    @Override
    public Predator create() {
        return new Predator(
                config.maxHp(),
                config.speed(),
                config.attack(),
                resourceFinder,
                logger
        );
    }
}