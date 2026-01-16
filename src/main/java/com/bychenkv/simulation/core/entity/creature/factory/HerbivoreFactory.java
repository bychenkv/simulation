package com.bychenkv.simulation.core.entity.creature.factory;

import com.bychenkv.simulation.config.HerbivoreConfig;
import com.bychenkv.simulation.core.entity.creature.Herbivore;
import com.bychenkv.simulation.core.finder.BfsResourceFinder;
import com.bychenkv.simulation.logger.SimulationLogger;

public class HerbivoreFactory extends CreatureFactory<Herbivore, HerbivoreConfig> {
    public HerbivoreFactory(HerbivoreConfig config,
                            BfsResourceFinder resourceFinder,
                            SimulationLogger logger) {
        super(config, resourceFinder, logger);
    }

    @Override
    public Herbivore create() {
        return new Herbivore(
                config.maxHp(),
                config.speed(),
                config.hpRestoreRate(),
                resourceFinder,
                logger
        );
    }
}
