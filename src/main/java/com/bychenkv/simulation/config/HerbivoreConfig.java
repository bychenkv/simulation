package com.bychenkv.simulation.config;

public final class HerbivoreConfig extends CreatureBaseConfig {
    private static final int DEFAULT_MAX_HP = 2;
    private static final int DEFAULT_SPEED = 2;
    private static final int DEFAULT_HP_RESTORE_RATE = 1;

    private final int hpRestoreRate;

    public HerbivoreConfig(int maxHp, int speed, int hpRestoreRate) {
        super(maxHp, speed);
        this.hpRestoreRate = hpRestoreRate;
    }

    public static HerbivoreConfig withDefaults() {
        return new HerbivoreConfig(DEFAULT_MAX_HP, DEFAULT_SPEED, DEFAULT_HP_RESTORE_RATE);
    }

    public int hpRestoreRate() {
        return hpRestoreRate;
    }
}
