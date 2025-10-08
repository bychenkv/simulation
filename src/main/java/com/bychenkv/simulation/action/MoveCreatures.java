package com.bychenkv.simulation.action;

import com.bychenkv.simulation.map.Position;
import com.bychenkv.simulation.map.SimulationMap;
import com.bychenkv.simulation.entity.creature.Creature;

import java.util.List;

public class MoveCreatures extends Action {
    @Override
    public void execute(SimulationMap map) {
        List<Creature> creatures = map.getEntitiesOfType(Creature.class);

        for (Creature creature : creatures) {
            Position currentPosition = map.getEntityPosition(creature);
            if (currentPosition != null) {
                creature.makeMove(map, currentPosition);
            }
        }
    }
}
