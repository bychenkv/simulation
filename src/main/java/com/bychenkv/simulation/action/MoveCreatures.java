package com.bychenkv.simulation.action;

import com.bychenkv.simulation.core.Position;
import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.creature.Creature;

import java.util.Map;

public class MoveCreatures extends Action {
    @Override
    public void execute(SimulationMap map) {
        for (Map.Entry<Position, Entity> entry : map.getEntityPositions().entrySet()) {
            if (entry.getValue() instanceof Creature creature) {
                creature.makeMove(map, entry.getKey());
            }
        }
    }
}
