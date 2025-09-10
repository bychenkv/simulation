package com.bychenkv.simulation.entity;

import com.bychenkv.simulation.Coordinate;
import com.bychenkv.simulation.Map;

public class Predator extends Creature {
    private final int attack;

    public Predator(int maxHp, int speed, int attack) {
        super(maxHp, speed);
        this.attack = attack;
        this.hpRestoreRate = attack;
    }

    @Override
    public boolean canConsume(Entity entity) {
        return entity instanceof Herbivore;
    }

    @Override
    protected void consumeResource(Map map, Coordinate resourceCoordinate) {
        Herbivore herbivore = (Herbivore) map.getEntity(resourceCoordinate);
        herbivore.takeDamage(attack);
        if (herbivore.isDead()) {
            System.out.println();
            map.removeEntity(resourceCoordinate);
        }
    }

    @Override
    public String toString() {
        return "\uD83D\uDC3A";
    }
}
