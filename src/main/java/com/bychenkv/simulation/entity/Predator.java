package com.bychenkv.simulation.entity;

import com.bychenkv.simulation.Map;

public class Predator extends Creature {
    private final int attack;

    public Predator(int hp, int speed, int attack) {
        super(hp, speed);
        this.attack = attack;
    }

    @Override
    public void makeMove(Map map) {
        // run finding herbivore algorithm
    }

    @Override
    public String toString() {
        return "\uD83E\uDD81";
    }
}
