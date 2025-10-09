package com.bychenkv.simulation.config;

public final class PredatorConfig extends CreatureBaseConfig {
    private static final int DEFAULT_MAX_HP = 2;
    private static final int DEFAULT_SPEED = 3;
    private static final int DEFAULT_ATTACK = 1;

    private final int attack;

    public PredatorConfig(int maxHp, int speed, int attack) {
        super(maxHp, speed);
        this.attack = attack;
    }

    public static PredatorConfig withDefaults() {
        return new PredatorConfig(DEFAULT_MAX_HP, DEFAULT_SPEED, DEFAULT_ATTACK);
    }

    public int attack() {
        return attack;
    }
}