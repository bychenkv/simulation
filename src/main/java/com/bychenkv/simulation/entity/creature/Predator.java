package com.bychenkv.simulation.entity.creature;

import com.bychenkv.simulation.core.Position;
import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.utils.ResourceFinder;

public class Predator extends Creature {
    private final int attack;

    public Predator(int maxHp, int speed, int attack, ResourceFinder resourceFinder) {
        super(maxHp, speed, resourceFinder);

        this.attack = attack;
        this.hpRestoreRate = attack;
    }

    @Override
    protected void consumeResourceAt(SimulationMap map, Position resourcePosition) {
        Herbivore herbivore = (Herbivore) map.getEntityAt(resourcePosition);
        System.out.println(this + " meet " + herbivore + " on " + resourcePosition +
                           " and deal " + attack + " damage to it");

        herbivore.takeDamage(attack);

        if (herbivore.isDead()) {
            System.out.println(herbivore + " is dead");
            map.removeEntity(resourcePosition);
        }
    }

    @Override
    public String toString() {
        return "\uD83D\uDC3A";
    }
}
