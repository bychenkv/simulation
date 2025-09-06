package com.bychenkv.simulation.action;

import com.bychenkv.simulation.Map;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.Tree;

public class ArrangeTreesAction extends ArrangeEntitiesAction {
    public ArrangeTreesAction(Map map, int treesNumber) {
        super(map, treesNumber);
    }

    @Override
    public Entity createEntity() {
        return new Tree();
    }
}
