package com.bychenkv.simulation.core.entity.creature;

import com.bychenkv.simulation.core.finder.BfsResourceFinder;
import com.bychenkv.simulation.core.map.Position;
import com.bychenkv.simulation.core.map.SimulationMap;
import com.bychenkv.simulation.core.entity.Entity;
import com.bychenkv.simulation.core.entity.object.Grass;
import com.bychenkv.simulation.logger.SimulationLogger;

public class Herbivore extends Creature {
    public Herbivore(int maxHp,
                     int speed,
                     int hpRestoreRate,
                     BfsResourceFinder resourceFinder,
                     SimulationLogger logger) {
        super(maxHp, speed, resourceFinder, logger);
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
        logger.info(this + " found " + grass + " on " + resourcePosition + " and eat it");
        map.removeEntityAt(resourcePosition);
    }

    @Override
    public String toString() {
        return Herbivore.class.getSimpleName();
    }
}
