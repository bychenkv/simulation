package com.bychenkv.simulation.config;

import com.bychenkv.simulation.map.MapBounds;

public record SimulationConfig(MapBounds mapBounds, EntityPopulationConfig populationConfig) {
    public static SimulationConfig defaultConfig() {
        return new SimulationConfig(
                MapBounds.defaultBounds(),
                EntityPopulationConfig.defaultConfig()
        );
    }
}
