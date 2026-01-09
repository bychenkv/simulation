package com.bychenkv.simulation.core.simulation;

import com.bychenkv.simulation.logger.SimulationLogger;

public class SimulationController {
    private final SimulationEngine engine;
    private final SimulationLogger logger;
    private final SimulationEventBus eventBus;
    private final SimulationState state;
    private final long iterationDelayMs;

    public SimulationController(SimulationEngine engine,
                                SimulationState state,
                                SimulationEventBus eventBus,
                                SimulationLogger logger,
                                long iterationDelayMs) {
        this.engine = engine;
        this.state = state;
        this.eventBus = eventBus;
        this.logger = logger;
        this.iterationDelayMs = iterationDelayMs;
    }
    
    public void start() {
        updateStatus(SimulationStatus.RUNNING);
        logger.info("Simulation started");

        try {
            engine.initialize();
            runSimulationLoop();
        } catch (InterruptedException e) {
            logger.warn("Simulation thread has been interrupted");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("Simulation fatal error: " + e.getMessage());
        }
    }

    public void pause() {
        if (state.isRunning()) {
            updateStatus(SimulationStatus.PAUSED);
            logger.info("Simulation paused");
        }
    }

    public void resume() {
        if (state.isPaused()) {
            updateStatus(SimulationStatus.RUNNING);
            logger.info("Simulation resumed");
        }
    }

    public void stop() {
        updateStatus(SimulationStatus.STOPPED);
        logger.info("Simulation stopped");
    }

    public void togglePause() {
        if (state.isPaused()) {
            resume();
        } else if (state.isRunning()) {
            pause();
        }
    }

    private void runSimulationLoop() throws InterruptedException {
        while (!state.isStopped()) {
            state.waitIfPaused();

            if (state.isStopped()) {
                break;
            }

            engine.executeIteration();
            Thread.sleep(iterationDelayMs);
        }
    }

    private void updateStatus(SimulationStatus status) {
        state.setStatus(status);
        eventBus.notifyStatusChanged(status);
    }
}
