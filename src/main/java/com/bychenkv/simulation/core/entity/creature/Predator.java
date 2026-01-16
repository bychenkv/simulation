package com.bychenkv.simulation.core.entity.creature;

import com.bychenkv.simulation.core.finder.BfsResourceFinder;
import com.bychenkv.simulation.core.map.Position;
import com.bychenkv.simulation.core.map.SimulationMap;
import com.bychenkv.simulation.core.entity.Entity;
import com.bychenkv.simulation.logger.SimulationLogger;

public class Predator extends Creature {
    private final int attack;

    public Predator(int maxHp,
                    int speed,
                    int attack,
                    BfsResourceFinder resourceFinder,
                    SimulationLogger logger) {
        super(maxHp, speed, resourceFinder, logger);
        this.attack = attack;
        this.hpRestoreRate = attack;
    }

    @Override
    public boolean canConsume(Entity entity) {
        return entity instanceof Herbivore;
    }

    @Override
    protected void consumeResourceAt(SimulationMap map, Position resourcePosition) {
        Herbivore herbivore = (Herbivore) map.getEntityAt(resourcePosition);
        logger.info(this + " found " + herbivore + " on " + resourcePosition +
                            " and deal " + attack + " damage to it");

        herbivore.takeDamage(attack);

        if (herbivore.isDead()) {
            logger.info(herbivore + " is dead");
            map.removeEntityAt(resourcePosition);
        }
    }

    @Override
    public String toString() {
        return Predator.class.getSimpleName();
    }
}
