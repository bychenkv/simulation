package com.bychenkv.simulation.entity.creature.factory;

import com.bychenkv.simulation.config.HerbivoreConfig;
import com.bychenkv.simulation.entity.creature.Herbivore;
import com.bychenkv.simulation.utils.ResourceFinder;

public class HerbivoreFactory implements CreatureFactory<Herbivore> {
    private final HerbivoreConfig config;
    private final ResourceFinder resourceFinder;

    public HerbivoreFactory(HerbivoreConfig config, ResourceFinder resourceFinder) {
        this.config = config;
        this.resourceFinder = resourceFinder;
    }

    @Override
    public Herbivore create() {
        return new Herbivore(
                config.maxHp(),
                config.speed(),
                config.hpRestoreRate(),
                resourceFinder
        );
    }
}
