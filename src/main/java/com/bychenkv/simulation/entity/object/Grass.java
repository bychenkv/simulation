package com.bychenkv.simulation.entity.object;

import com.bychenkv.simulation.entity.Consumable;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.creature.Herbivore;

public class Grass extends Entity implements Consumable {
    @Override
    public boolean canBeConsumedBy(Entity entity) {
        return entity instanceof Herbivore;
    }

    @Override
    public String toString() {
        return "\uD83C\uDF31";
    }
}
