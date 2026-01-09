package com.bychenkv.simulation.config;

public record SimulationConfig(
        EntitySpawnConfig spawn,
        CreaturesConfig creatures
) {
    public static SimulationConfig defaults() {
        return new SimulationConfig(
                EntitySpawnConfig.withDefaults(),
                CreaturesConfig.withDefaults()
        );
    }
}
