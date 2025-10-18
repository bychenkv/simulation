package com.bychenkv.simulation.action;

import com.bychenkv.simulation.core.SimulationContext;
import com.bychenkv.simulation.core.SimulationEventBus;
import com.bychenkv.simulation.map.Position;
import com.bychenkv.simulation.map.SimulationMap;
import com.bychenkv.simulation.entity.creature.Creature;
import com.bychenkv.simulation.services.rendering.MapRenderer;

import java.util.List;

public class MoveCreatures extends Action {
    private static final long TIMEOUT = 500L;

    private final MapRenderer mapRenderer;
    private final SimulationEventBus eventBus;

    public MoveCreatures(MapRenderer mapRenderer, SimulationEventBus eventBus) {
        this.mapRenderer = mapRenderer;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(SimulationContext context) {
        SimulationMap map = context.getMap();
        List<Creature> creatures = map.getEntitiesOfType(Creature.class);

        for (Creature creature : creatures) {
            if (context.isStopped()) {
                break;
            }

            try {
                context.waitIfPaused();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            if (context.isStopped()) {
                break;
            }

            Position currentPosition = map.getEntityPosition(creature);
            if (currentPosition != null) {
                creature.makeMove(map, currentPosition);
                eventBus.notifyMapRendered(mapRenderer.render());
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
