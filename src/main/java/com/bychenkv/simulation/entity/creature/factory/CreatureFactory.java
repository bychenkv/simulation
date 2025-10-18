package com.bychenkv.simulation.entity.creature.factory;

import com.bychenkv.simulation.config.CreatureBaseConfig;
import com.bychenkv.simulation.entity.creature.Creature;
import com.bychenkv.simulation.services.finder.ResourceFinder;
import com.bychenkv.simulation.services.logger.SimulationLogger;

public abstract class CreatureFactory<T extends Creature, C extends CreatureBaseConfig> {
    protected final C config;
    protected final ResourceFinder resourceFinder;
    protected final SimulationLogger logger;

    public CreatureFactory(C config, ResourceFinder resourceFinder, SimulationLogger logger) {
        this.config = config;
        this.resourceFinder = resourceFinder;
        this.logger = logger;
    }

    public abstract T create();
}