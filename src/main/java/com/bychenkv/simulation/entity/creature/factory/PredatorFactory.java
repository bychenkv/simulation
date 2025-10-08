package com.bychenkv.simulation.entity.creature.factory;

import com.bychenkv.simulation.config.PredatorConfig;
import com.bychenkv.simulation.entity.creature.Predator;
import com.bychenkv.simulation.utils.ResourceFinder;

public class PredatorFactory implements CreatureFactory<Predator> {
    private final PredatorConfig config;
    private final ResourceFinder resourceFinder;

    public PredatorFactory(PredatorConfig config, ResourceFinder resourceFinder) {
        this.config = config;
        this.resourceFinder = resourceFinder;
    }

    @Override
    public Predator create() {
        return new Predator(
                config.maxHp(),
                config.speed(),
                config.attack(),
                resourceFinder
        );
    }
}