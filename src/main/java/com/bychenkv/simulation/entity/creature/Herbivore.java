package com.bychenkv.simulation.entity.creature;

import com.bychenkv.simulation.map.Position;
import com.bychenkv.simulation.map.SimulationMap;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.object.Grass;
import com.bychenkv.simulation.services.finder.ResourceFinder;

public class Herbivore extends Creature {
    public Herbivore(int maxHp, int speed, int hpRestoreRate, ResourceFinder resourceFinder) {
        super(maxHp, speed, resourceFinder);
        this.hpRestoreRate = hpRestoreRate;
    }

    public void takeDamage(int damage) {
        currentHp = Math.max(0, currentHp - damage);
    }

    public boolean isDead() {
        return currentHp == 0;
    }

    @Override
    public boolean canConsume(Entity entity) {
        return entity instanceof Grass;
    }

    @Override
    protected void consumeResourceAt(SimulationMap map, Position resourcePosition) {
        Grass grass = (Grass) map.getEntityAt(resourcePosition);
        // System.out.println(this + " meet " + grass + " on " + resourcePosition + " and eat it");

        map.removeEntityAt(resourcePosition);
    }

    @Override
    public String toString() {
        return Herbivore.class.getSimpleName();
    }
}
