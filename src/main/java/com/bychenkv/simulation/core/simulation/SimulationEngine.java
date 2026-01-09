package com.bychenkv.simulation.core.simulation;

import com.bychenkv.simulation.core.action.Action;
import com.bychenkv.simulation.core.action.SimulationActions;
import com.bychenkv.simulation.core.map.SimulationMap;
import com.bychenkv.simulation.logger.SimulationLogger;

public class SimulationEngine {
    private final SimulationMap map;
    private final SimulationActions actions;
    private final SimulationEventBus eventBus;
    private final SimulationLogger logger;
    private final SimulationState state;

    public SimulationEngine(SimulationMap map,
                            SimulationActions actions,
                            SimulationEventBus eventBus,
                            SimulationLogger logger,
                            SimulationState state) {
        this.map = map;
        this.actions = actions;
        this.eventBus = eventBus;
        this.logger = logger;
        this.state = state;
    }

    public void initialize() {
        logger.info("Initializing simulation...");

        state.resetIterationCount();

        for (Action action : actions.init()) {
            action.execute(map, state);
        }
    }

    public void executeIteration() {
        if (!state.isRunning()) {
            logger.warn("Simulation is already running");
            return;
        }

        int currentIteration = state.incrementIterationCount();
        eventBus.notifyIterationCompleted(currentIteration);

        for (Action action : actions.turn()) {
            if (state.isRunning()) {
                action.execute(map, state);
            }
        }
    }
}
