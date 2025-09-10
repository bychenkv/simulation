package com.bychenkv.simulation.action;

import com.bychenkv.simulation.Map;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.Predator;

public class ArrangePredatorsAction extends ArrangeEntitiesAction {
    private static final int DEFAULT_PREDATOR_HP = 2;
    private static final int DEFAULT_PREDATOR_SPEED = 3;
    private static final int DEFAULT_PREDATOR_ATTACK = 1;

    public ArrangePredatorsAction(Map map, int predatorsNumber) {
        super(map, predatorsNumber);
    }

    @Override
    public Entity createEntity() {
        return new Predator(DEFAULT_PREDATOR_HP, DEFAULT_PREDATOR_SPEED, DEFAULT_PREDATOR_ATTACK);
    }
}
