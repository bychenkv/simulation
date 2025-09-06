package com.bychenkv.simulation.entity;

import com.bychenkv.simulation.Map;

public class Herbivore extends Creature {
    public Herbivore(int hp, int speed) {
        super(hp, speed);
    }

    @Override
    public void makeMove(Map map) {
        // run finding grass algorithm
    }
}
