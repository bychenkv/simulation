package com.bychenkv.simulation.core.entity.creature.factory;

import com.bychenkv.simulation.config.PredatorConfig;
import com.bychenkv.simulation.core.entity.creature.Predator;
import com.bychenkv.simulation.core.finder.BfsResourceFinder;
import com.bychenkv.simulation.logger.SimulationLogger;

public class PredatorFactory extends CreatureFactory<Predator, PredatorConfig> {
    public PredatorFactory(PredatorConfig config,
                           BfsResourceFinder resourceFinder,
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