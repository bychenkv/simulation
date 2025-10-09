package com.bychenkv.simulation.entity.creature.factory;

import com.bychenkv.simulation.config.HerbivoreConfig;
import com.bychenkv.simulation.entity.creature.Herbivore;
import com.bychenkv.simulation.utils.ResourceFinder;

public class HerbivoreFactory extends CreatureFactory<Herbivore, HerbivoreConfig> {
    public HerbivoreFactory(HerbivoreConfig config, ResourceFinder resourceFinder) {
        super(config, resourceFinder);
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
