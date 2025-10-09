package com.bychenkv.simulation.config;


public class CreatureBaseConfig {
    protected final int maxHp;
    protected final int speed;

    public CreatureBaseConfig(int maxHp, int speed) {
        this.maxHp = maxHp;
        this.speed = speed;
    }

    public int maxHp() {
        return maxHp;
    }

    public int speed() {
        return speed;
    }
}
