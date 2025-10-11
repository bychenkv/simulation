package com.bychenkv.simulation.entity.creature.factory;

import com.bychenkv.simulation.config.CreatureBaseConfig;
import com.bychenkv.simulation.entity.creature.Creature;
import com.bychenkv.simulation.services.finder.ResourceFinder;

public abstract class CreatureFactory<T extends Creature, C extends CreatureBaseConfig> {
    protected final C config;
    protected final ResourceFinder resourceFinder;

    public CreatureFactory(C config, ResourceFinder resourceFinder) {
        this.config = config;
        this.resourceFinder = resourceFinder;
    }

    public abstract T create();
}