package com.bychenkv.simulation.core;

import com.bychenkv.simulation.action.Action;
import com.bychenkv.simulation.services.logger.SimulationLogger;
import com.bychenkv.simulation.services.rendering.MapRenderer;

public class Simulation {
    private static final long ITERATION_DELAY_MS = 500;

    private final MapRenderer mapRenderer;
    private final SimulationActions actions;
    private final SimulationEventBus eventBus;
    private final SimulationLogger logger;

    private final SimulationContext context;

    public Simulation(MapRenderer mapRenderer,
                      SimulationActions actions,
                      SimulationEventBus eventBus,
                      SimulationLogger logger,
                      SimulationContext context) {
        this.mapRenderer = mapRenderer;
        this.actions = actions;
        this.eventBus = eventBus;
        this.logger = logger;
        this.context = context;
    }

    public void start() {
        logger.info("Simulation starting...");

        try {
            initializeSimulation();

            while (!context.isStopped()) {
                if (!waitIfPaused()) {
                    break;
                }
                executeIteration();
                //noinspection BusyWait
                 Thread.sleep(ITERATION_DELAY_MS);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void initializeSimulation() {
        logger.info("Initializing simulation...");

        updateStatus(SimulationStatus.RUNNING);
        context.resetIterationCount();

        for (Action action : actions.init()) {
            action.execute(context);
        }
    }

    private void executeIteration() {
        int currentIteration = context.incrementIterationCount();
        String renderedMap = mapRenderer.render();

        eventBus.notifyIterationCompleted(currentIteration, renderedMap);

        for (Action action : actions.turn()) {
            if (context.isRunning()) {
                action.execute(context);
            }
        }
    }

    private synchronized boolean waitIfPaused() throws InterruptedException {
        context.waitIfPaused();
        return !context.isStopped();
    }

    public synchronized void pause() {
        updateStatus(SimulationStatus.PAUSED);
        logger.info("Simulation paused");
    }

    public synchronized void resume() {
        updateStatus(SimulationStatus.RUNNING);
        logger.info("Simulation resumed");
        context.notifyResumed();
    }

    public synchronized void stop() {
        updateStatus(SimulationStatus.STOPPED);
        logger.info("Simulation stopping...");
        context.notifyResumed();
    }

    public boolean isPaused() {
        return context.isPaused();
    }

    private void updateStatus(SimulationStatus newStatus) {
        context.setStatus(newStatus);
        eventBus.notifyStatusChanged(newStatus);
    }
}
