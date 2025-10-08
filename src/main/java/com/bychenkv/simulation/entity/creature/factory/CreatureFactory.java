package com.bychenkv.simulation.entity.creature.factory;

import com.bychenkv.simulation.entity.creature.Creature;

public interface CreatureFactory<T extends Creature> {
    T create();
}