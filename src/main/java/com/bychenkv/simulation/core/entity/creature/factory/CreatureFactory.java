package com.bychenkv.simulation.core.entity.creature.factory;

import com.bychenkv.simulation.config.CreatureBaseConfig;
import com.bychenkv.simulation.core.entity.creature.Creature;
import com.bychenkv.simulation.core.finder.BfsResourceFinder;
import com.bychenkv.simulation.logger.SimulationLogger;

public abstract class CreatureFactory<T extends Creature, C extends CreatureBaseConfig> {
    protected final C config;
    protected final BfsResourceFinder resourceFinder;
    protected final SimulationLogger logger;

    public CreatureFactory(C config, BfsResourceFinder resourceFinder, SimulationLogger logger) {
        this.config = config;
        this.resourceFinder = resourceFinder;
        this.logger = logger;
    }

    public abstract T create();
}