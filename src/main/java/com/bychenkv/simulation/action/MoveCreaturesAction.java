package com.bychenkv.simulation.action;

import com.bychenkv.simulation.core.Position;
import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.creature.Creature;

import java.util.Map;

public class MoveCreaturesAction extends Action {
    public MoveCreaturesAction(SimulationMap map) {
        super(map);
    }

    @Override
    public void execute() {
        for (Map.Entry<Position, Entity> entry : map.getEntityPositions().entrySet()) {
            if (entry.getValue() instanceof Creature creature) {
                creature.makeMove(map, entry.getKey());
            }
        }
    }
}
