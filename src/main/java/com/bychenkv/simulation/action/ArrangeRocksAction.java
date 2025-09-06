package com.bychenkv.simulation.action;

import com.bychenkv.simulation.Map;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.Rock;

public class ArrangeRocksAction extends ArrangeEntitiesAction {
    public ArrangeRocksAction(Map map, int rocksNumber) {
        super(map, rocksNumber);
    }

    @Override
    public Entity createEntity() {
        return new Rock();
    }
}
