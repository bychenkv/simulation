package com.bychenkv.simulation.config;

public record PredatorConfig(CreatureBaseConfig baseConfig, int attack) {
    private static final int DEFAULT_MAX_HP = 2;
    private static final int DEFAULT_SPEED = 3;
    private static final int DEFAULT_ATTACK = 1;

    public static PredatorConfig withDefaults() {
        return new PredatorConfig(
                new CreatureBaseConfig(DEFAULT_MAX_HP, DEFAULT_SPEED),
                DEFAULT_ATTACK
        );
    }

    public int maxHp() {
        return baseConfig.maxHp();
    }

    public int speed() {
        return baseConfig.speed();
    }
}
