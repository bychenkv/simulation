package com.bychenkv.simulation.action;

import com.bychenkv.simulation.Coordinate;
import com.bychenkv.simulation.Map;
import com.bychenkv.simulation.entity.Creature;
import com.bychenkv.simulation.entity.Entity;

public class MoveCreaturesAction extends Action {
    public MoveCreaturesAction(Map map) {
        super(map);
    }

    @Override
    public void execute() {
        for (java.util.Map.Entry<Coordinate, Entity> entry : map.getEntities().entrySet()) {
            if (entry.getValue() instanceof Creature creature) {
                creature.makeMove(map, entry.getKey());
            }
        }
    }
}
