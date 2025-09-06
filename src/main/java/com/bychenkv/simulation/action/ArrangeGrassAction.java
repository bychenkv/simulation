package com.bychenkv.simulation.action;

import com.bychenkv.simulation.Map;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.Grass;

public class ArrangeGrassAction extends ArrangeEntitiesAction {
    public ArrangeGrassAction(Map map, int grassNumber) {
        super(map, grassNumber);
    }

    @Override
    public Entity createEntity() {
        return new Grass();
    }
}
