package com.bychenkv.simulation.core.action;

import com.bychenkv.simulation.core.simulation.SimulationEventBus;
import com.bychenkv.simulation.core.simulation.SimulationState;
import com.bychenkv.simulation.core.map.Position;
import com.bychenkv.simulation.core.map.SimulationMap;
import com.bychenkv.simulation.core.entity.creature.Creature;

import java.util.List;

public class MoveCreatures extends Action {
    private static final long TIMEOUT = 500L;

    private final SimulationEventBus eventBus;

    public MoveCreatures(SimulationEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void execute(SimulationMap map, SimulationState state) {
        List<Creature> creatures = map.getEntitiesOfType(Creature.class);

        for (Creature creature : creatures) {
            if (state.isStopped()) {
                break;
            }

            try {
                state.waitIfPaused();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            if (state.isStopped()) {
                break;
            }

            Position currentPosition = map.getEntityPosition(creature);
            if (currentPosition != null) {
                creature.makeMove(map, currentPosition);
                eventBus.notifyMapRendered();
            }
            try {
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
