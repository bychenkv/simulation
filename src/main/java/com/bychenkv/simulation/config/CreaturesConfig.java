package com.bychenkv.simulation.config;

public record CreaturesConfig(HerbivoreConfig herbivore, PredatorConfig predator) {
    public static CreaturesConfig withDefaults() {
        return new CreaturesConfig(
                HerbivoreConfig.withDefaults(),
                PredatorConfig.withDefaults()
        );
    }
}
