package com.bychenkv.simulation.config;

public record EntitySpawnConfig(
        int rocks,
        int trees,
        int grass,
        int herbivores,
        int predators
) {
    private static final int DEFAULT_ROCKS = 5;
    private static final int DEFAULT_TREES = 5;
    private static final int DEFAULT_GRASS = 5;
    private static final int DEFAULT_HERBIVORES = 5;
    private static final int DEFAULT_PREDATORS = 5;

    public static EntitySpawnConfig withDefaults() {
        return new EntitySpawnConfig(
                DEFAULT_ROCKS,
                DEFAULT_TREES,
                DEFAULT_GRASS,
                DEFAULT_HERBIVORES,
                DEFAULT_PREDATORS
        );
    }
}
