package com.bychenkv.simulation.config;

public record HerbivoreConfig(CreatureBaseConfig baseConfig, int hpRestoreRate) {
    private static final int DEFAULT_MAX_HP = 2;
    private static final int DEFAULT_SPEED = 2;
    private static final int DEFAULT_HP_RESTORE_RATE = 1;

    public static HerbivoreConfig withDefaults() {
        return new HerbivoreConfig(
                new CreatureBaseConfig(DEFAULT_MAX_HP, DEFAULT_SPEED),
                DEFAULT_HP_RESTORE_RATE
        );
    }

    public int maxHp() {
        return baseConfig.maxHp();
    }

    public int speed() {
        return baseConfig.speed();
    }
}
