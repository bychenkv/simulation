package com.bychenkv.simulation.action;

import com.bychenkv.simulation.core.Position;
import com.bychenkv.simulation.core.SimulationMap;
import com.bychenkv.simulation.entity.Entity;
import com.bychenkv.simulation.entity.creature.Creature;

import java.util.Map;

public class MoveCreatures extends Action {
    @Override
    public void execute(SimulationMap map) {
        Map<Position, Entity> entityPositions = map.getEntityPositions();

        for (Entity entity : entityPositions.values()) {
            if (entity instanceof Creature creature) {
                Position currentPosition = map.getEntityPosition(creature);
                if (currentPosition != null) {
                    creature.makeMove(map, currentPosition);
                }
            }
        }
    }
}
