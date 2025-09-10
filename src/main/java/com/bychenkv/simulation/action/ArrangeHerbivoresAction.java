package com.bychenkv.simulation.action;

import com.bychenkv.simulation.Map;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.Herbivore;

public class ArrangeHerbivoresAction extends ArrangeEntitiesAction {
    private static final int DEFAULT_HERBIVORE_HP = 2;
    private static final int DEFAULT_HERBIVORE_SPEED = 2;
    private static final int DEFAULT_HP_RESTORE_RATE = 1;

    public ArrangeHerbivoresAction(Map map, int herbivoresNumber) {
        super(map, herbivoresNumber);
    }

    @Override
    public Entity createEntity() {
        return new Herbivore(DEFAULT_HERBIVORE_HP, DEFAULT_HERBIVORE_SPEED, DEFAULT_HP_RESTORE_RATE);
    }
}
