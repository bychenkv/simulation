package com.bychenkv.simulation.config;

import com.bychenkv.simulation.map.MapBounds;

public record SimulationConfig(
        MapBounds mapBounds,
        EntitySpawnConfig spawn,
        CreaturesConfig creatures
) {
    public static SimulationConfig withDefaults() {
        return new SimulationConfig(
                MapBounds.withDefaults(),
                EntitySpawnConfig.withDefaults(),
                CreaturesConfig.withDefaults()
        );
    }
}
