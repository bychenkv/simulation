package com.bychenkv.simulation.entity;

import com.bychenkv.simulation.Map;

public abstract class Creature extends Entity {
    protected int hp;
    protected int speed;

    public Creature(int hp, int speed) {
        this.hp = hp;
        this.speed = speed;
    }

    public abstract void makeMove(Map map);
}
